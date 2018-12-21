package cn.edu.scu.raizerugu;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.edu.scu.raizerugu.interfaces.IServerConfiguration;

public class ServerConfiguration implements IServerConfiguration {

	public static InetAddress getDefaultAddress() {
		try {
			return InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
		} catch (UnknownHostException uhe) {
			return null;
		}
	}

	public static int getDefaultPort() {
		return 5020;  // shapes near yuzu
	}
	
	private InetAddress bindAddress;
	private int port;
	private String serverRoot;
	
	public InetAddress getBindAddress() {
		return bindAddress;
	}

	public int getPort() {
		return port;
	}
	
	public IServerConfiguration setBindAddress(InetAddress bindAddr) {
		this.bindAddress = bindAddr;
		return this;
	}
	
	public IServerConfiguration setPort(int port) {
		this.port = port;
		return this;
	}

	public String getServerRoot() {
		return serverRoot;
	}

	public void setServerRoot(String serverRoot) {
		this.serverRoot = serverRoot;
	}
}
