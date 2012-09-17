package server.management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


public class PoolThread extends Thread  {
	private static PoolThread instance = null;
	
	private static Vector amadeus;
	
	
	public PoolThread() {
		if (instance == null){
			amadeus = new Vector();
		}
	}

	public static PoolThread getInstance(){
		if(instance == null){
			instance = new PoolThread();
		}
		return instance;
	}
	
	public void run() {
		instance = getInstance();
		Iterator i;
		IthreadAmadeus tAmadeus = null; 
		
		while ( true)
			try {
				//Tive que fazer com a cópia, pois estava gerando uma exceção
				//java.util.ConcurrentModificationException
				i = new ArrayList(amadeus).iterator();
				
				while (i.hasNext()){
					tAmadeus = (IthreadAmadeus) i.next();
					tAmadeus.work();
				}
				
				sleep(50);
				
			} catch (IOException e) {
				// 
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public void newIthreadAmadeus(IthreadAmadeus itAmadeus){
		amadeus.addElement(itAmadeus);
	}
	
}
