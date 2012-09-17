package br.ufpe.cin.middleware.stub_skeleton.transport;

import java.io.Serializable;
import java.util.Vector;

public class Buffer<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	/* uma vez que não teremos camada de apresentação,
	 * usaremos um array de objetos mesmo.
	 */
	public Vector<T> container;
	
	public Buffer(int size) {
		container = new Vector<T>(size);
	}
	
	public T read() {
		T a = container.lastElement();
		if (a != null) container.remove(a);
		return a;
	}
	
	public void write(T object) {
		container.add(object);
	}
	
}
