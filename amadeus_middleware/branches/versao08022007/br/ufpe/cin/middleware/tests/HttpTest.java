package br.ufpe.cin.middleware.tests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import br.ufpe.cin.middleware.util.Debug;

public class HttpTest extends HttpURLConnection {

	public HttpTest(URL arg0) {
		super(arg0);
	}

	public void disconnect() {
		
	}

	public boolean usingProxy() {
		return false;
	}

	public void connect() throws IOException {
		
	}
	
	public static void main(String[] args) {
		try {
			URL u = new URL("http","www.cin.ufpe.br","/~mrsq");
			System.out.println("OK");
			URLConnection connection = u.openConnection();
			connection.connect();
			System.out.println(connection.getContentType());
			
		} catch (MalformedURLException e) {
			Debug.printStack(e);
		} catch (IOException e) {
			Debug.printStack(e);
		}
	}
	
	

}
