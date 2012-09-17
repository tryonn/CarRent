package br.ufpe.cin.middleware.services.security;

import br.ufpe.cin.middleware.services.naming.Naming;

public class DBWrapper_client {
	
	public static void main(String[] args) {
		System.out.println("Cliente do BD inicializado!");
		DBWrapper db = null;
		try {
			db = (DBWrapper) Naming.resolve("DBWrapper");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(db.validateLogin("aluno", "aluno"));
	}
}
