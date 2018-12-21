package cn.edu.scu.raizerugu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cn.edu.scu.raizerugu.interfaces.IServer;
import cn.edu.scu.raizerugu.interfaces.IServerConfiguration;

public class Server implements IServer {
	
	public static final String VERSION = "Raizerugu/0.2.1";
	public static final float HTTP_VERSION = 1.1f;

	public void start(IServerConfiguration serverConfiguration) {
		ContentManager.getInstance().setServerRoot(serverConfiguration.getServerRoot());
		
		ServerSocket welcomeSocket;
		try {
			welcomeSocket = new ServerSocket(serverConfiguration.getPort(), 
					0, serverConfiguration.getBindAddress());

			Logger.info(String.format("server starts at %s:%d", 
					serverConfiguration.getBindAddress().getHostAddress(),
					serverConfiguration.getPort()));

			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				new ServiceThread(connectionSocket).start();
			}
		} catch (IOException ioe) {
			Logger.fatal(ioe.getLocalizedMessage());
		}
	}

	public void stop() {
		Logger.info("server stopped");
		System.exit(0);
	}

}
