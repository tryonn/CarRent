package br.ufpe.cin.middleware.services.session;

import java.io.Serializable;

public class MicroMundo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name = null;
	private String port = null;
	private String keepAlive = null;

	public MicroMundo(String name, String port) {
		this.name = name;
		this.port = port;
		this.generateKeepAlive();
	}
	
	public MicroMundo(String name, String port, String keep) {
		this(name,port);
		this.keepAlive = keep;
	}
	
	//gerar um novo keepAlive
	public void generateKeepAlive() {
		this.keepAlive = String.valueOf(System.currentTimeMillis());
	}
	
	//para facilitar o un-parsing
	public String toString() {
		return "<micromundo name=\"" + this.name +"\" port=\"" + this.port + "\" keepAlive=\"" + this.keepAlive + "\"></micromundo>";
	}
	
	//para comparar corretamente
	public boolean equals(Object obj) {
		boolean retorno = false;
		if ((obj != null) || (obj instanceof MicroMundo)) {
			MicroMundo obj2 = (MicroMundo) obj;
			retorno = (this.name.equalsIgnoreCase(obj2.name))
				   && (this.port.equalsIgnoreCase(obj2.port));
		}
		return retorno;
	}
	
	
	/*            GETS E SETS            */

	
	
	public String getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
