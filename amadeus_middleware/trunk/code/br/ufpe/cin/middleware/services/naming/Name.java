package br.ufpe.cin.middleware.services.naming;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class Name implements Serializable {

	private static final long serialVersionUID = 1L;

	Vector<NameComponent> component = new Vector<NameComponent>(10);
	
	public boolean equals(Object o) {
		if (o instanceof Name) {
			return (o.hashCode() == this.hashCode());			
		} else {
			return super.equals(o);
		}
	}

	public void addComponent(NameComponent nc) {
		this.component.add(nc);
	}
	
	public Name reduce_to_one() {
		Name n = new Name();
		n.addComponent(this.component.get(0));
		return n;
	}
	
	public int hashCode() {
		int hash = 0;
		Iterator<NameComponent> e1 = this.component.iterator();
		while(e1.hasNext()) {
		    hash += e1.next().hashCode();
		}
		return hash;
	}
	
}
