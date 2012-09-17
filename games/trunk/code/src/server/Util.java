package server;

public class Util {

	public static StringBuffer getParametro(int nParametro, StringBuffer msg){
		int pos = 9;
		int tamanho = 0;
		StringBuffer res = new StringBuffer("");
		String aux = new String("");
		
			for (int i=1; i <= nParametro; i++){
				tamanho = Integer.parseInt( msg.substring(pos , pos +5 ) );
				aux = msg.substring(pos +5 ,pos + tamanho + 5) ;
				pos = pos + 5 + tamanho ;
			}
		res.append(aux);
		return res;
	}
	
	public static String sizeParameter(int size,String str){
		
		String res = new String("");
		res = "00000" + Integer.toString( str.length() );
		
		return res.substring( res.length()-size, res.length() );
	}
	
}
