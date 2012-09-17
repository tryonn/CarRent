package workgroup.server;


import java.net.Socket;

import workgroup.handler.LoginHandler;
import workgroup.server.services.SocketService;

public class WorkGroupService extends SocketService {

	public WorkGroupService(PlattusServer server, int port) {
		super(server,"Workgroup Service", port);
	}

	@Override
	public void serve(Socket socket) throws Exception {
		new LoginHandler(getServer(), socket);
	}

}
