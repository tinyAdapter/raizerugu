package cn.edu.scu.raizerugu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HTTPRequestParser {
	
	public static HTTPRequest parseFrom(InputStream in) 
			throws IOException {
		HTTPRequest request = new HTTPRequest();
		
		BufferedReader inFromClient = new BufferedReader(
				new InputStreamReader(in));
		
		String[] methodPathHttpVersion = inFromClient.readLine().split(" ");
		parseMethod(request, methodPathHttpVersion[0]);
		request.setPath(methodPathHttpVersion[1]);
		parseHTTPVersion(request, methodPathHttpVersion[2]);
		while (inFromClient.ready()) {
			String currentLine = inFromClient.readLine().toLowerCase();
			if (currentLine.startsWith("host: ")) {
				request.setHost(currentLine.substring(6));
			} else if (currentLine.startsWith("connection: ")) {
				request.setConnection(currentLine.substring(12));
			} else if (currentLine.startsWith("user-agent: ")) {
				request.setUserAgent(currentLine.substring(12));
			} else if (currentLine.startsWith("accpet-language: ")) {
				request.setAcceptLanguage(currentLine.substring(17));
			} else if (currentLine.startsWith("if-modified-since: ")) {
				request.setIfModifiedSince(
						ZonedDateTime.parse(
								currentLine.substring(19), 
								DateTimeFormatter.RFC_1123_DATE_TIME));
			} else {
				// ignore unsupported headers
			}
		}
		
		return request;
	}
	
	private static void parseMethod(HTTPRequest request, String methodString) {
		if (methodString.equals("GET"))
			request.setMethod(HTTPMethod.GET);
		else if (methodString.equals("POST"))
			request.setMethod(HTTPMethod.POST);
		else if (methodString.equals("PUT"))
			request.setMethod(HTTPMethod.PUT);
		else if (methodString.equals("DELETE"))
			request.setMethod(HTTPMethod.DELETE);
		else {
			Logger.error(String.format("no such method type: %s", methodString));
		}
	}
	
	private static void parseHTTPVersion(HTTPRequest request, String HTTPVersionString) {
		request.setHttpVersion(Float.parseFloat(HTTPVersionString.substring(5)));
	}
}
