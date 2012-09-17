package workgroup.server.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigProperties implements Iterable<ConfigProperty> {
    // Internal data
    //
    private Map<String, ConfigProperty> map = new HashMap<String, ConfigProperty>();


    /**
     *
     */
    public ConfigProperties()
    { }
    /**
     *
     */
    public ConfigProperties(Map<String, ConfigProperty> map)
    {
        this.map = map;
    }
    /**
     *
     */
    public ConfigProperties(ConfigProperty[] properties)
    {
        for (int i=0; i<properties.length; i++)
        {
            map.put(properties[i].getName(), properties[i]);
        }
    }
    /**
     *
     */
    public ConfigProperties(ConfigProperties src)
    {
        try
        {
            map.putAll(src.map);
        }
        catch (UnsupportedOperationException uoe)
        {
            // Should never happen--we're using HashMap internally,
            // and HashMap supports everything in Map
        }
        catch (ClassCastException cce)
        {
            // Should never happen
        }
        catch (IllegalArgumentException iae)
        {
            // Ditto
        }
        catch (NullPointerException npe)
        {
            // How can it be null?
        }
    }
    /**
     *
     */
    public ConfigProperties(ConfigProperties src, ConfigProperty[] properties)
    {
        try
        {
            // Store ConfigProperty instances in 'src'
            map.putAll(src.map);
        
            // Store ConfigProperty instances in 'properties'
            for (int i=0; i<properties.length; i++)
            {
                map.put(properties[i].getName(), properties[i]);
            }
        }
        catch (UnsupportedOperationException uoe)
        {
            // Should never happen--we're using HashMap internally,
            // and HashMap supports everything in Map
        }
        catch (ClassCastException cce)
        {
            // Should never happen
        }
        catch (IllegalArgumentException iae)
        {
            // Ditto
        }
        catch (NullPointerException npe)
        {
            // How can it be null?
        }
    }


    /**
     *
     */
    public ConfigProperty get(String name)
    {
        return map.get(name);
    }
    
    /**
     *
     */
    public Serializable getValue(String name)
    {
        return map.get(name).getValue();
    }
    
    /**
     *
     */
    public void set(String name, Serializable value)
    {
        map.get(name).setValue(value);
    }
    
    /**
     *
     */
    public void set(ConfigProperty prop)
    {
        map.put(prop.getName(), prop);
    }
    
    /**
     *
     */
    public void set(ConfigProperties props)
    {
    	for (ConfigProperty prop : props) {
    		map.put(prop.getName(), prop);
		}
    }


    /**
     *
     */
    public void remove(String name)
    {
        map.remove(name);
    }
    /**
     *
     */
    public void remove(ConfigProperty prop)
    {
        remove(prop.getName());
    }


    /**
    *
    */
   public Iterator<ConfigProperty> properties()
   {
       return iterator();
   }
   
   /**
   *
   */
   public Iterator<ConfigProperty> iterator()
   {
       return map.values().iterator();
   }
}
