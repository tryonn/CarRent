package workgroup.server.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigProperty implements Serializable {

	private static final long serialVersionUID = 11L;

    // Internal implementation
    //
    private String m_name = null;
    private String m_classType = null;
    private String[] m_compatibleTypes = new String[0];
    private Serializable m_value = null;
    private String m_desc = null;

    private transient Method m_parseMethod = null;
    private String m_parseMethodClass = null;
    private String m_parseMethodSig = null;

    
    /**
     *
     */
    public ConfigProperty()
    { }
    /**
     * Simplified version: assumes no compatible types, and no
     * "stringified" parser is specified
     *
     * @param name The name of the property
     * @param value The initial value of the property; <B>must not
     *     be null!<B> ConfigProperty uses the class type of this
     *     parameter to determine the class type of the property.
     * @param desc A human-readable description of the property
     */
    public ConfigProperty(String name, Object value, String desc)
    {
        setBaseInfo(name, value.getClass(), null, desc, null);
        m_value = (Serializable)value;
    }
    /**
     *
     */
    public ConfigProperty(String name, Class classType,
                          String[] compatibleTypes, String desc,
                          Method parser)
    {
        setBaseInfo(name, classType, compatibleTypes, desc, parser);
    }
    /**
     *
     */
    public ConfigProperty(String name, Class classType,
                          String[] compatibleTypes,
                          Serializable value, String desc,
                          Method parser)
    {
        setBaseInfo(name, classType, compatibleTypes, desc, parser);
        m_value = value;
    }


    /**
     *
     */
    public void setBaseInfo(String name, Class classType,
                            String[] compatibleTypes, String desc,
                            Method parser)
    {
        m_name = name;
        m_classType = classType.toString();
        m_compatibleTypes = compatibleTypes;
        if (m_compatibleTypes == null)
        {
            m_compatibleTypes = new String[0];
        }
        m_desc = desc;

        if (parser != null)
        {
            m_parseMethod = parser;
            m_parseMethodClass = m_parseMethod.getDeclaringClass().toString();
            m_parseMethodSig = m_parseMethod.toString();
        }
    }


    /**
     *
     */
    public String getName()
    {
        return new String(m_name);
    }
    /**
     *
     */
    public String getClassType()
    {
        return new String(m_classType);
    }
    /**
     *
     */
    public String[] getCompatibleTypes()
    {
        String[] ret = new String[m_compatibleTypes.length];
        for (int i=0; i<ret.length; i++)
        {
            ret[i] = new String(m_compatibleTypes[i]);
        }

        return ret;
    }
    /**
     *
     */
    public String getDescription()
    {
        return new String(m_desc);
    }


    /**
     *
     */
    public Serializable getValue()
    {
        return m_value;
    }
    /**
     *
     */
    public String getValueClass()
    {
        return m_value.getClass().toString();
    }
    /**
     *
     */
    public void setValue(Serializable value)
    {
        String valueClass = value.getClass().toString();

        // If it's the exact type, we're OK
        if (valueClass.equals(m_classType))
        {
            m_value = value;
            return;
        }

        // If the names match exactly, we're OK
        for (int i=0; i<m_compatibleTypes.length; i++)
        {
            if (m_compatibleTypes[i].equals(valueClass))
            {
                m_value = value;
                return;
            }
        }

        // If we're still here, the value failed to convert
        throw new RuntimeException();
    }
    /**
     *
     */
    public void setValue(String stringifiedValue)
    {
        // Test for parsers already in place (those types already
        // provided by Java; this will work for 95% of the time)

        // java.lang.* types
        if (m_classType.equals(String.class.toString()))
        {
            m_value = stringifiedValue;
        }
        else if (m_classType.equals(StringBuffer.class.toString()))
        {
            m_value = new StringBuffer(stringifiedValue);
        }
        else if (m_classType.equals(Boolean.class.toString()))
        {
            m_value = new Boolean(stringifiedValue);
        }
        else if (m_classType.equals(Byte.class.toString()))
        {
            m_value = new Byte(stringifiedValue);
        }
        else if (m_classType.equals(Character.class.toString()))
        {
            //m_value = new Character(stringifiedValue);
        }
        else if (m_classType.equals(Double.class.toString()))
        {
            m_value = new Double(stringifiedValue);
        }
        else if (m_classType.equals(Float.class.toString()))
        {
            m_value = new Float(stringifiedValue);
        }
        else if (m_classType.equals(Integer.class.toString()))
        {
            m_value = new Integer(stringifiedValue);
        }
        else if (m_classType.equals(Long.class.toString()))
        {
            m_value = new Long(stringifiedValue);
        }
        else if (m_classType.equals(Short.class.toString()))
        {
            m_value = new Short(stringifiedValue);
        }
        // java.math.* types
        else if (m_classType.equals(BigDecimal.class.toString()))
        {
            m_value = new BigDecimal(stringifiedValue);
        }
        else if (m_classType.equals(BigInteger.class.toString()))
        {
            m_value = new BigInteger(stringifiedValue);
        }
        // java.util.* types
        else if (m_classType.equals(Date.class.toString()))
        {
            DateFormat df = new SimpleDateFormat();
            try
            {
                m_value = df.parse(stringifiedValue);
            }
            catch (Exception ex)
            { }
        }
        // Well, it's not a "standard" type, so we've got to
        // try and parse it
        else
        {
            try
            {
                // We have to parse the stringified value
                if (m_parseMethod == null &&
                    m_parseMethodClass != null &&
                    m_parseMethodSig != null)
                {
                        Class c = Class.forName(m_parseMethodClass);
                        Method[] methods =
                            c.getDeclaredMethods();
                        for (int i=0; i<methods.length; i++)
                        {
                            String methString = methods[i].toString();
                            if (methString.equals(m_parseMethodSig))
                            {
                                m_parseMethod = methods[i];
                                break;
                            }
                        }
                }

                if (m_parseMethod == null)
                {
                    // We tried; nothing more to do
                    return;
                }
    
                // Is it static, or virtual?
                int mods = m_parseMethod.getModifiers();

                Object instance = null;

                if (Modifier.isStatic(mods))
                {
                    // We can call the Method directly; no instance
                    // needed in order to do so
                }
                else
                {
                    // We have to try and instantiate the Class type
                    // in order to call on the Method
                    Class c = Class.forName(m_parseMethodClass);
                    instance = c.newInstance();
                }
                
                Object[] args = new Object[]
                {
                    stringifiedValue
                };
                m_value = (Serializable)
                    m_parseMethod.invoke(instance, args);
            }
            catch (Exception ex)
            {
                // We can't do anything with it; just give up
            }
        }
    }

    public String toString()
    {
        return new String("ConfigProperty: " +
                          getName() + " (" +
                          getClassType() + "): " +
                          getValue().toString());
    }
}
