package br.ufpe.cin.middleware.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XMLParser {
	
	public static String CONNECTOR = null;
	public static String[] COMPONENTS = null;
	
	public static Hashtable<String,Integer> localPorts = null;
	public static Hashtable<String,Integer> serverPorts = null;
	public static Hashtable<String,String> hosts = null;
	
	//not instantiable class
	private XMLParser() {}
	
	static {
		parse();
	}

	public static void parse() {
		Document d = parseXmlFile("config.xml");
		Node amadeus = d.getFirstChild();
		if (amadeus.getNodeName().equals("amadeus")) {
			amadeus.removeChild(amadeus.getFirstChild());
			NodeList nd = amadeus.getChildNodes();
			for (int i = 0; i < nd.getLength(); i += 2) {
				Node n = nd.item(i);
				String nodeName = n.getNodeName();
				if ((nodeName.equals("connectors")) || (nodeName.equals("components")) ||
						(nodeName.equals("hosts"))) {
					//removendo inúteis
					n.removeChild(n.getFirstChild());
					NodeList child = n.getChildNodes();
					for (int j = 1; j < child.getLength(); j++) {
						n.removeChild(child.item(j));
					}
					int count = child.getLength();
					Vector<String> compTemp = new Vector<String>(count);
					for (int j = 0; j < count; j++) {
						Node sub = child.item(j);
						NamedNodeMap attr = sub.getAttributes();
						if (nodeName.equals("connectors")) {
							if (sub.getNodeName().equals("logicConnector")) {
								Node use = attr.getNamedItem("use");
								if ((use != null) && (use.getNodeValue().equals("true"))) {
									CONNECTOR = attr.getNamedItem("value").getNodeValue();
								}
							}
						} else if (nodeName.equals("components")){
							if (sub.getNodeName().equals("component")) {
								Node use = attr.getNamedItem("use");
								if ((use != null) && (use.getNodeValue().equals("true"))) {
									compTemp.add(attr.getNamedItem("value").getNodeValue());
								}
							}
						} else {
							if (sub.getNodeName().equals("host")) {
								if (localPorts == null) {
									localPorts = new Hashtable<String, Integer>(count);
									serverPorts = new Hashtable<String, Integer>(count);
									hosts = new Hashtable<String, String>(count);
								}
								//name="namingService" host="localhost" localPort="5399" remotePort="1099";
								String name = attr.getNamedItem("name").getNodeValue();
								String host = attr.getNamedItem("host").getNodeValue();
								String localPort = attr.getNamedItem("localPort").getNodeValue();
								String serverPort = attr.getNamedItem("remotePort").getNodeValue();
								
								localPorts.put(name, Integer.parseInt(localPort));
								serverPorts.put(name, Integer.parseInt(serverPort));
								hosts.put(name, host);
							}
						}
					}
					if (compTemp.size() > 0) {
						COMPONENTS = new String[compTemp.size()];
						for (int j = 0; j < COMPONENTS.length; j++) {
							COMPONENTS[j] = compTemp.elementAt(j);
						}
					}
				}
			}
		}
		if ((CONNECTOR == null) || (COMPONENTS == null)) {
			throw new IllegalArgumentException("Não foi possível fazer parsing do arquivo de entrada!!");
		}
			
	}

	/**
	 * Parses an XML file and returns a DOM document.
	 * 
	 * @param filename the name of XML file
	 * @param validating If true, the contents is validated against the DTD specified in the file.
	 * @return the parsed document
	 */
	private static Document parseXmlFile(String filename) {
		Document doc = null;
		DocumentBuilderFactory factory = null;
		//  Create a builder factory
		try {
			factory = DocumentBuilderFactory.newInstance();
		} catch (FactoryConfigurationError e) {
			Debug.error(XMLParser.class, e);
			return null;
		}
		factory.setValidating(false);
		// Create the builder and parse the file
		try {
			try {
				// try to get the file from jar
				InputStream instream = XMLParser.class.getResourceAsStream(filename);
				doc = factory.newDocumentBuilder().parse(instream);
			} catch (Exception ejar) {
				try {
					// try to get the file as external URI
					doc = factory.newDocumentBuilder().parse(filename);
				} catch (IOException euri) {
					try {
						// try to get the file as local filename
						doc = factory.newDocumentBuilder().parse(new File(filename));
					} catch (IOException efile) {
						try {
							// try to resolve the path as relative to local class folder
							String[] classPath = System.getProperties().getProperty("java.class.path", ".").split(";");
							String newpath = classPath[0] + "/" + filename;
							doc = factory.newDocumentBuilder().parse(new File(newpath));
						} catch (IOException epath) {
							// unable to get the input file
							Debug.error(XMLParser.class, "IOException:" + epath);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			Debug.error(XMLParser.class, "[" + filename + "] ParserConfigurationException:" + e);
		} catch (SAXException e) {
			Debug.error(XMLParser.class, "[" + filename + "] SAXException:" + e);
		}
		return doc;
	}

}