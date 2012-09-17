package br.ufpe.cin.middleware.tests;

import br.ufpe.cin.middleware.services.session.Session;
import br.ufpe.cin.middleware.services.session.Session_XMLParser;

public class SessionFileTest {

	public static void main(String[] args) {
		Session session = Session_XMLParser.readFromFile();
		
		System.out.println(session.getKey() + session.getUserHost() + "\n" + session.getUserLogin() + "\n" +
				session.getStartingTime() + "\n" + session.getMicromundos().elementAt(0));
	}
}
