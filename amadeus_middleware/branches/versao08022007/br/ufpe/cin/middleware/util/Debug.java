package br.ufpe.cin.middleware.util;

import java.io.PrintStream;

/**
 * 
 * Classe utilizada para fazer depuração de código
 * no Amadeus Middleware.
 *  
 * @author Bruno Barros (blbs@cin.ufpe.br) 
 * @author Rebeka Gomes (rgo2@cin.ufpe.br)
 *
 */
public class Debug {
	
	private static int counter = 0;
	private static boolean debugMode = true;

	private static void print(String tipo, String clazz, Object s) {
		System.err.println(String.format("%10s %03d \t %s : %s",tipo,counter++,clazz,s));
	}

	/**
	 * Imprime uma mensagem de erro (sempre será impressa).
	 * 
	 * @param t classe que deseja imprimir.
	 * @param s mensagem a ser impressa.
	 */
	public static void error(Class t, Object s) {
		print("ERRO", t.getSimpleName(), s);
	}
	
	/**
	 * Imprime uma mensagem de aviso, que será impressa
	 * dependendo do estágio atual (debug ou execução).
	 *
	 * @param t classe que deseja imprimir.
	 * @param s mensagem a ser impressa.
	 */
	public static void warning(Class t, Object s) {
		if (debugMode) print("DEBUG", t.getSimpleName(), s);
	}
	
	/**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * @param s <code>PrintStream</code> to use for output
     */
    public static void printStack(Throwable e) {
    	if (!debugMode) {
    		e.printStackTrace();
    	} else {
	    	PrintStream s = System.out;
	    	synchronized (s) {
	            s.println(e);
	            StackTraceElement[] trace = e.getStackTrace();
	            for (int i=0; i < trace.length; i++) {
	            	//String tmp[] = trace[i].getClassName().split("\\.");
	            	String tmp [] = {"0",trace[i].getClassName()};
	            	String m = tmp[tmp.length - 1] + "." + trace[i].getMethodName() + 
	                (trace[i].isNativeMethod() ? "(Native Method)" :
	                 (trace[i].getFileName() != null && trace[i].getLineNumber() >= 0 ?
	                  "(" + trace[i].getFileName() + ":" + trace[i].getLineNumber() + ")" :
	                  (trace[i].getFileName() != null ?  "("+trace[i].getFileName()+")" : "" )));
	            	s.println("\tat " + m);
	            }
	
	            Throwable ourCause = e.getCause();
	            if (ourCause != null) {
	            	printStack(ourCause);
	            }
	        }
    	}
    }

}
