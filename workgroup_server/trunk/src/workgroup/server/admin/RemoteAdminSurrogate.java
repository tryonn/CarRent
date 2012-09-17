package workgroup.server.admin;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import workgroup.message.AdminMessage;
import workgroup.server.ServerModel;
import workgroup.server.ServerModelListener;
import workgroup.server.ServerStateView;

public class RemoteAdminSurrogate implements ServerModelListener {

	InetAddress host;
	int port;
	int failTries = 0;
	
	public RemoteAdminSurrogate(InetAddress host, int port) {
		this.host = host;
		this.port = port;
		ServerModelChange(new ServerStateView(ServerModel.getInstance()));
	}
	
	public void ServerModelChange(ServerStateView stateView) {
		try {
			// Envia ServerStateView
			Socket socket = new Socket(host, port);
			OutputStream outToServer = socket.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(outToServer);
			out.writeObject(new AdminMessage(AdminMessage.ADMIN_MODEL_CHANGE, stateView));
			out.close();
			failTries = 0;
		} catch (IOException e) {
			failTries = failTries + 1;
			if (failTries > 10) {
				ServerModel.getInstance().removeListener(this);
			}
		}	
	}
}
