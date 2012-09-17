package br.ufpe.cin.middleware.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Buffer<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<T> container;
	
	public Buffer() {
		this(10);
	}
	
	public Buffer(int size) {
		this.container = Collections.synchronizedList(new ArrayList<T>(size));
	}
	
	public synchronized void write(T o) {
		if (o != null) {
			this.container.add(0,o);
		}
	}
	
	public synchronized T read() {
		T a = null;
		int tam = this.container.size();
		if (tam > 0) {
			a = this.container.get(tam - 1);
			if (a != null) this.container.remove(a);
		}
		return a;
	}
	
	public synchronized Iterator<T> iterator() {
		return this.container.iterator();
	}

	public synchronized boolean remove(T o) {
		return this.container.remove(o);
	}

	
	
}
