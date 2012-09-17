package br.ufpe.cin.middleware.services.session;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.channels.FileLock;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufpe.cin.middleware.util.Debug;


public final class Session_XMLParser {

	private static FileLock LOCK = null;
	private static RandomAccessFile FILE = null;

	public static void writeSessionToFile(Session session) {
		if (session != null) {
			String sb = new String();
			sb += ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			sb += ("<session>\n");
			sb += ("     <key key=\"" + session.getKey() + "\"></key>\n");
			sb += ("     <connection host=\"" + session.getUserHost() + "\"></connection>\n");
			sb += ("     <private login=\"" + session.getUserLogin() + "\" time=\"" + session.getStartingTime().getTime() + "\"></private>\n");
			for (MicroMundo mm : session.getMicromundos()) {
				sb += ("     " + mm.toString() + "\n");
			}
			sb += ("</session>\n");

			writeInFile(sb);

		}
	}

	private static void writeInFile(String content) {
		if (LOCK == null) {
			pegarLock();
		} 
		try {
			FILE.seek(0);
			FILE.setLength(0);
			byte [] bytes = content.getBytes();
			FILE.write(bytes,0,bytes.length);
			System.out.println(content);
		} catch (IOException e) {
			Debug.error(Session_XMLParser.class, "IOException:" + e);
		} 
	}

	public static Session readFromFile() {
		if (LOCK == null) {
			pegarLock();
		}
		Session retorno = new Session();
		String name = null;
		String port = null;
		String keepAlive = null;
		Document d = parseXmlFile("session.xml");
		if (d != null) {
			Node session = d.getFirstChild();
			if (session.getNodeName().equals("session")) {
				session.removeChild(session.getFirstChild());
				NodeList nd = session.getChildNodes();
				for (int i = 0; i < nd.getLength(); i += 2) {
					Node n = nd.item(i);
					String nodeName = n.getNodeName();
					NamedNodeMap attr = n.getAttributes();
					if (nodeName.equals("connection")) {
						retorno.setUserHost(attr.getNamedItem("host")
								.getNodeValue());
					} else if (nodeName.equals("private")) {
						retorno.setUserLogin(attr.getNamedItem("login")
								.getNodeValue());
						retorno.setStartingTime(new Date(Long.valueOf(attr
								.getNamedItem("time").getNodeValue())));
					} else if (nodeName.equals("micromundo")) {
						name = attr.getNamedItem("name").getNodeValue();
						port = attr.getNamedItem("port").getNodeValue();
						keepAlive = attr.getNamedItem("keepAlive")
						.getNodeValue();
						retorno.getMicromundos().add(
								new MicroMundo(name, port, keepAlive));
					} else if (nodeName.equals("key")) {
						retorno.setKey(attr.getNamedItem("key").getNodeValue());
					} 
				}
			}
		}		
		return retorno;
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
			Debug.error(Session_XMLParser.class, e);
			return null;
		}
		factory.setValidating(false);
		// Create the builder and parse the file

		try {
			try {
				byte [] bytes = new byte [(int) FILE.length()];
				FILE.seek(0);
				FILE.read(bytes);
				doc = factory.newDocumentBuilder().parse(new ByteArrayInputStream(bytes));
			} catch (ParserConfigurationException e) {
				Debug.error(Session_XMLParser.class, "[" + filename + "] ParserConfigurationException:" + e);
			} catch (SAXException e) {
				Debug.error(Session_XMLParser.class, "[" + filename + "] SAXException:" + e);
			}
		} catch (IOException e) {
			Debug.error(Session_XMLParser.class, "IOException: " + e);
		}
		return doc;
	}

	public static boolean pegarLock() {
		if (LOCK == null) {
			try {
				FILE = new RandomAccessFile("session.xml", "rw");
				LOCK = FILE.getChannel().lock();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				Debug.error(Session_XMLParser.class, "IOException" + e);
				return false;
			}
		} else {
			throw new RuntimeException("Lock já está com outro objeto!");
		}
	}

	public static boolean liberarLock() {
		if (LOCK != null) {
			try {
				LOCK.release();
				LOCK = null;
				FILE.close();
				FILE = null;
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				Debug.error(Session_XMLParser.class, "IOException" + e);
				return false;
			}			
		} else {
			throw new RuntimeException("Não há lock para ser liberado!");
		}
	}

	public static boolean fileExists() {
		return new File("session.xml").exists();
	}

	public static void main(String[] args) throws Exception {
		Session x = Session_XMLParser.readFromFile();
		x.getKey();
		writeSessionToFile(x);
		x.setKey(InetAddress.getLocalHost().toString());
		x.setStartingTime(new Date(System.currentTimeMillis()));
		x.setUserHost("G2C05");
		x.setUserLogin("blbs");
		writeSessionToFile(x);
	}


}


