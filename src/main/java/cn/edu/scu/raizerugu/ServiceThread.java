package cn.edu.scu.raizerugu;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ServiceThread extends Thread {

	private Socket socket;
	public ServiceThread(Socket connectionSocket) {
		this.socket = connectionSocket;
	}
	
	public void run() {
		try {
			HTTPRequest request;
			try {
				request = HTTPRequestParser.parseFrom(socket.getInputStream());
			} catch (Exception e) {
				Logger.error(e.getLocalizedMessage());
				socket.close();
				return;
			}
			
			DataOutputStream outToClient = new DataOutputStream(
					socket.getOutputStream());
			
			HTTPResponse response = makeResponse(request);
			outToClient.write(response.getBytes());
			Logger.info(String.format("%s:%s -> %s %s ", 
					socket.getInetAddress().getHostAddress(),
					socket.getPort(),
					response.getStatusCode(),
					request.getPath()
					));
			
			socket.close();
		} catch (IOException ioe) {
			Logger.fatal(ioe.getLocalizedMessage());
		}
	}
	
	private HTTPResponse makeResponse(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setHttpVersion(Server.HTTP_VERSION);
		response.setConnection("close");
		response.setDate(new Date());
		
		File file = ContentManager.getInstance().getFileFromPath(request.getPath());
		if (file.exists()) {
			if (request.hasIfModifiedSinceHeader()) {
				if (file.lastModified() / 1000 == 
						request.getIfModifiedSince()
							   .toInstant().toEpochMilli() / 1000) {
					response.setStatusCode(HTTPStatusCode.NOT_MODIFIED);
				}
			} else if (file.getPath().endsWith(".html")) {
				response.setStatusCode(HTTPStatusCode.OK);
				response.setContentType("text/html; charset=utf-8");
				response.setLastModified(new Date(file.lastModified()));
				response.setContent(
						ContentManager.getInstance().getBytesFromPath(file.toPath()));
			} else if (request.getPath().endsWith(".css")) {
				response.setStatusCode(HTTPStatusCode.OK);
				response.setContentType("text/css; charset=utf-8");
				response.setLastModified(new Date(file.lastModified()));
				response.setContent(
						ContentManager.getInstance().getBytesFromPath(file.toPath()));
			} else if (request.getPath().endsWith(".js")) {
				response.setStatusCode(HTTPStatusCode.OK);				
				response.setContentType("application/javascript; charset=utf-8");
				response.setLastModified(new Date(file.lastModified()));
				response.setContent(
						ContentManager.getInstance().getBytesFromPath(file.toPath()));
			} else if (request.getPath().endsWith(".jpg")) {
				response.setStatusCode(HTTPStatusCode.OK);
				response.setContentType("image/jpeg");
				response.setLastModified(new Date(file.lastModified()));
				response.setContent(
						ContentManager.getInstance().getBytesFromPath(file.toPath()));
			} else if (request.getPath().endsWith(".png")) {
				response.setStatusCode(HTTPStatusCode.OK);
				response.setContentType("image/png");
				response.setLastModified(new Date(file.lastModified()));
				response.setContent(
						ContentManager.getInstance().getBytesFromPath(file.toPath()));
			} else {
				response.setStatusCode(HTTPStatusCode.FORBIDDEN);
				response.setContentType("text/html; charset=utf-8");
				response.setContent(getHTTPErrorHTMLText(HTTPStatusCode.FORBIDDEN).getBytes());
			}
		} else {
			response.setStatusCode(HTTPStatusCode.NOT_FOUND);
			response.setContentType("text/html");
			response.setContent(getHTTPErrorHTMLText(HTTPStatusCode.NOT_FOUND).getBytes());
		}
		
		return response;
	}
	
	private String getHTTPErrorHTMLText(String statusCode) {
		return String.format(
				"<html>\r\n" + 
				"<head><title>%s</title></head>\r\n" + 
				"<body bgcolor=\"white\">\r\n" + 
				"<center><h1>%s</h1></center>\r\n" + 
				"<hr><center>%s</center>\r\n" + 
				"</body>\r\n" + 
				"</html>", statusCode, statusCode, Server.VERSION);
	}
}
