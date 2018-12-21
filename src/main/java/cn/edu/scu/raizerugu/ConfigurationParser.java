package cn.edu.scu.raizerugu;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.LinkOption;

import cn.edu.scu.raizerugu.interfaces.IServerConfiguration;

public class ConfigurationParser {

	public static IServerConfiguration parseFrom(String[] args) {
		ServerConfiguration serverConfiguration = new ServerConfiguration();
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-p") && args.length >= i + 2) {
				try {
					serverConfiguration.setPort(Integer.parseInt(args[i+1]));
				} catch(NumberFormatException nfe) {
					Logger.fatal(nfe.getLocalizedMessage());
				}
			}
			if (args[i].equals("-h") && args.length >= i + 2) {
				try {
					serverConfiguration.setBindAddress(InetAddress.getByName(args[i+1]));
				} catch(UnknownHostException uhe) {
					Logger.fatal(uhe.getLocalizedMessage());
				}
			}
			if (args[i].equals("-r") && args.length >= i + 2) {
				String serverRoot = args[i+1];
				if (!(new File(serverRoot).isDirectory())) {
					Logger.fatal(String.format("no such server root directory: %s", serverRoot));
				}
				serverConfiguration.setServerRoot(serverRoot);
			}
		}
		
		if (serverConfiguration.getBindAddress() == null) {
			serverConfiguration.setBindAddress(ServerConfiguration.getDefaultAddress());
		}
		if (serverConfiguration.getPort() == 0) {
			serverConfiguration.setPort(ServerConfiguration.getDefaultPort());
		}
		if (serverConfiguration.getServerRoot() == null) {
			Logger.fatal("must specific server root folder");
		}
		
		return serverConfiguration;
	}
}
