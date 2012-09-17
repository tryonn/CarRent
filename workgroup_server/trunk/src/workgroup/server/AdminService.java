package workgroup.server;


import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import workgroup.message.AdminMessage;
import workgroup.server.admin.RemoteAdminSurrogate;
import workgroup.server.services.SocketService;

public class AdminService extends SocketService {

	public AdminService(PlattusServer server, int port) {
		super(server, "Admin Service", port);
	}

	@Override
	public void serve(Socket socket) throws Exception {
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		AdminMessage message = (AdminMessage) in.readObject();
		//String login = (String) message.getParameter(0);
		//String senha = (String) message.getParameter(1);
		int porta = Integer.parseInt((String) message.getParameter(2));
		InetAddress host = socket.getInetAddress();
		RemoteAdminSurrogate admin = new RemoteAdminSurrogate(host, porta);
		ServerModel.getInstance().addListener(admin);
	}

}
