package br.ufpe.cin.middleware.services.session;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.Vector;

import br.ufpe.cin.middleware.services.naming.Naming;


public class Session implements Serializable {
	
	private String key = null;
	private String userHost = null;
	private String userLogin = null;
	private Date startingTime = null;
	private Vector<MicroMundo> micromundos = null;
	
	// atributos criados para implementar os métodos indicados por Rebeka
	private transient MicroMundo mmRepresentante = null;
	private transient Thread runner = null;
	private transient SessionService stub;

	public static final int TIME_OUT_SECONDS = 10;
	private static final long serialVersionUID = 1L;
	
	protected Session() {
		this.micromundos = new Vector<MicroMundo>();
		this.runner = new Thread() {
			public void run() {
				Session.this.monitorarSessao();
			}
		};
	}
	
	public Session(MicroMundo micromundo, String key, String userHost, String userLogin, Date startHour) {
		this();
		if (micromundo == null || (key == null) || userHost == null || userLogin == null || startHour == null) {
			throw new IllegalArgumentException ("Não é possível criar uma sessão com algum dos parâmetros nulos!");
		}
		this.mmRepresentante = micromundo;
		this.key = key;
		this.userHost = userHost;
		this.userLogin = userLogin;
		this.startingTime = startHour;
		this.insertMicroMundo(this.mmRepresentante);
		this.runner.start();
	}
	
	public void insertMicroMundo(MicroMundo microMundo){
		this.micromundos.add(microMundo);
	}
	
	public void removeMicroMundo(MicroMundo microMundo){
		this.micromundos.remove(microMundo);
	}

	public void monitorarSessao() {
		while (this.runner.isAlive()) {
			try {
				this.mmRepresentante.generateKeepAlive();
				boolean existeArquivo = Session_XMLParser.fileExists();
				Session_XMLParser.pegarLock();
				Session session = this;
				if (existeArquivo) {
					session = Session_XMLParser.readFromFile();
					session.mmRepresentante = this.mmRepresentante;
					if (!session.userLogin.equalsIgnoreCase(this.userLogin)) {
						//TODO o que vai ser feito caso já tenha uma sessão com outro usuário?!
					}
					if (session.micromundos.contains(this.mmRepresentante)) {
						session.atualizarMicroMundoRepresentante();
					} else {
						session.insertMicroMundo(this.mmRepresentante);
					}
					this.atualizarSession(session);
				}
				Session_XMLParser.writeSessionToFile(session);
				Session_XMLParser.liberarLock();

				sendToServer();
				
				Thread.sleep(TIME_OUT_SECONDS * 1000); // esperar algum tempo antes de verificar de novo
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sendToServer() throws Exception {
		//enviar para o servidor
		if (this.stub == null) {
			stub = (SessionService) Naming.resolve("SessionService");
			stub.create(this);
		} else {
			stub.update(this);
		}
	}

	private void atualizarSession(Session session) {
		this.key = session.key;
		this.micromundos = session.micromundos;
		this.mmRepresentante = session.mmRepresentante;
		this.startingTime = session.startingTime;
		this.userHost = session.userHost;
		this.userLogin = session.userLogin;
	}

	private void atualizarMicroMundoRepresentante() {
		for (int i = 0; i < this.micromundos.size(); i++) {
			MicroMundo mm = this.micromundos.get(i);
			if (mm.equals(this.mmRepresentante)) {
				this.micromundos.set(i, this.mmRepresentante);
			}
		}
	}
	

	
	
	
	/*            GETS E SETS            */
	
	
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Vector<MicroMundo> getMicromundos() {
		return micromundos;
	}
	public void setMicromundos(Vector<MicroMundo> micromundos) {
		this.micromundos = micromundos;
	}
	public Date getStartingTime() {
		return startingTime;
	}
	public void setStartingTime(Date startHour) {
		this.startingTime = startHour;
	}
	public String getUserHost() {
		return userHost;
	}
	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	
	public static void main(String[] args) throws Exception {
		byte [] bs = new byte [1024];
		System.out.println("Digite o nome: ");
		String mmName = new String(bs,0,System.in.read(bs)).trim();
		System.out.println("Digite a porta: ");
		String mmPort = new String(bs,0,System.in.read(bs)).trim();
		System.out.println("Digite o login: ");
		String mmLogin = new String(bs,0,System.in.read(bs)).trim();
		new Session (
				new MicroMundo(mmName,mmPort),
				InetAddress.getLocalHost().getHostAddress(),
				InetAddress.getLocalHost().getHostAddress(),
				mmLogin,
				new Date(System.currentTimeMillis()));
	}

	public boolean create(Session session) throws Exception {
		if (stub != null)		
		return stub.create(session);
		return false;
	}

	public Session[] getUserbyMicroMundo(String microMundo) {
		if (stub != null)
		return stub.getUserbyMicroMundo(microMundo);
		return null;
	}

	public Session[] getUsers() {
		if (stub != null)
		return stub.getUsers();
		return null;
	}

	public boolean remove(Session session) throws Exception {
		if (stub != null)
		return stub.remove(session);
		return false;
	}

	public boolean update(Session session) throws Exception {
		if (stub != null)
		return stub.update(session);
		return false;
	}
	
}
