
package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bd.JogosDTO;



public class AddGame {

	
	public static final String XML_HEADER = "<?xml version=\"1.0\" ?>";
	public static final String CONTENT_TYPE = "text/xml;charset=UTF8";
	public static final String XML_FAILURE = XML_HEADER + "<package> <response> failure </response> </package>";
	public static final String XML_URL = XML_HEADER + "<package> <response> url error </response> </package>";
	public static final String XML_OK = XML_HEADER + "<package> <response> ok </response> </package>";
	
	
	public StringBuffer newGame(String nm_jogo,StringBuffer caminho, int curso, StringBuffer comentario){
		StringBuffer res = new StringBuffer();

		try {//Try para testar se a URL é válida
			URL url = new URL( caminho.toString() );
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection(  );
			BufferedReader in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
			in.close();
			urlcon.disconnect();
			
				try {//Try para testar se existe o arquivo de configuração			
					int max_user = 0;
					int min_user = 0;

					url = new URL( URLCorreta(caminho) );
					urlcon = (HttpURLConnection) url.openConnection(  );			
					urlcon.setRequestMethod("GET");
					urlcon.setRequestProperty("Content-type", "text/plain");   
				    urlcon.setDoInput(true);   
				    urlcon.setDoOutput(false);
				    in = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
				    String s = new String();

				    while (null != ((s = in.readLine()))) {
				        String parametro = getParametro(s);
				        if (parametro.equals("MAX_USER")){
				        	max_user = getValue(s);
				        	
				        }else{
					        if (parametro.equals("MIN_USER")){
					        	min_user = getValue(s);
					        }		        	
				        }    
				    }
				    in.close();
				    urlcon.getResponseMessage();
				    
					JogosDTO newGame = new JogosDTO();
					if ( newGame.insertNewGame(nm_jogo,max_user,min_user,caminho,1,curso,comentario) == 1 )
						res.append(XML_OK);
					else
						res.append( XML_FAILURE );
				    
				} catch (MalformedURLException e) {
					System.out.println("URL mal feita!");
					res.append( XML_URL );
				} catch (IOException e) {
					System.out.println("Jogo Avulso");
					
					JogosDTO newGame = new JogosDTO();
					if ( newGame.insertNewGame(nm_jogo,0,0,caminho,0,curso,comentario) == 1 )
						res.append(XML_OK);
					else
						res.append( XML_FAILURE );
				}

		} catch (MalformedURLException e1) {
			System.out.println("URL mal feita!");
			res.append( XML_URL );			
		}
		catch (IOException e) {
			System.out.println("URL não existe!");
			res.append( XML_URL );
		}
		

		
				
		return res;
	}

	private String getParametro(String msg){
		return msg.substring(0,msg.indexOf("=")).trim().toUpperCase();
	}
	
	private int getValue(String msg){
		String value = msg.substring(msg.indexOf("=")+1).trim().toUpperCase();
		return Integer.parseInt(value);
	}

	public String URLCorreta(StringBuffer url){
		String res = new String();
		if ( !url.substring(0, 7).toUpperCase().equals("HTTP://") ){
			res = "http://";
		}
		if ( url.lastIndexOf(".") != -1){
			res = res + url.substring(0, url.lastIndexOf(".")+1 ) + "conf"; 
		}


		return res;
	}

}





















