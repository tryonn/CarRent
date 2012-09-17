package br.ufpe.cin.middleware.namingService;

import java.io.Serializable;

public class NameComponent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String id;
	public String kind;
	
	public NameComponent(String id, String kind) {
		this.id = id;
		this.kind = kind;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof NameComponent) {
			NameComponent obj2 = (NameComponent) obj;
			boolean equals = (this.id.equals(obj2.id) &&
					this.kind.equals(obj2.kind));
			return equals;
		}
		return super.equals(obj);
	}
	
	public int hashCode() {
		int hash = 0;
		hash = (id.hashCode() << 3) + kind.hashCode()*5;
		return hash;
	}

}
