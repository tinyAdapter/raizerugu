package cn.edu.scu.raizerugu;

import cn.edu.scu.raizerugu.interfaces.IServer;
import cn.edu.scu.raizerugu.interfaces.IServerConfiguration;

public class Main {
	
	public static void main(String[] args) {
		IServer server = new Server();
		
		IServerConfiguration configuration = ConfigurationParser.parseFrom(args);
		server.start(configuration);
	}
	
	public static void printUsage() {
		System.out.println("Usage: ");
		System.out.println("  -h <BIND_ADDRESS>    specify bind address (default 0.0.0.0)");
		System.out.println("  -p <PORT_NUMBER>     specify listen port (default 5020)");
		System.out.println("  -r <ROOT_FOLDER>     set server root folder");
		System.out.println("");
	}

}
