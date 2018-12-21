package cn.edu.scu.raizerugu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;


public class ContentManager {
	
	private static ContentManager instance = new ContentManager();
	public static ContentManager getInstance() {
		return instance;
	}
	private ContentManager() {}
	
	private String serverRoot;
	
	public String getServerRoot() {
		return serverRoot;
	}
	public void setServerRoot(String serverRoot) {
		this.serverRoot = serverRoot;
	}
	
	public File getFileFromPath(String path) {
		Path filePath;
		if (path.equals("/")) {
			// client is requesting default object
			// ONLY available option: index.html
			filePath = Paths.get(getServerRoot(), "/index.html");
		} else {
			filePath = Paths.get(getServerRoot(), path);
		}
		return filePath.toFile();
	}
	
	private ConcurrentHashMap<String, byte[]> fileCache = new ConcurrentHashMap<String, byte[]>();
	
	public byte[] getBytesFromPath(Path path) {
		try {
			if (fileCache.get(path.toString()) != null) {
				return fileCache.get(path.toString());
			} else {
				byte[] fileBytes = Files.readAllBytes(path);
				fileCache.put(path.toString(), fileBytes);
				return fileBytes;
			}
		} catch (IOException ioe) {
			Logger.error(ioe.getLocalizedMessage());
			return null;
		}
	}
}
