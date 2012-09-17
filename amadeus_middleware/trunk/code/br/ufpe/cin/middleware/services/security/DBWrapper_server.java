package br.ufpe.cin.middleware.services.security;

import br.ufpe.cin.middleware.services.naming.Naming;
import br.ufpe.cin.middleware.services.naming.RemoteProcess;

public class DBWrapper_server {
	public static void main(String[] args) {
		try {
			Naming.bind("DBWrapper", new RemoteProcess("baia22",1512, new DBWrapper_impl()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
