package cn.edu.scu.raizerugu.interfaces;

import java.net.InetAddress;

public interface IServerConfiguration {
	InetAddress getBindAddress();
	int getPort();
	String getServerRoot();
}
