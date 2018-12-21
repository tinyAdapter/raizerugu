package cn.edu.scu.raizerugu;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HTTPResponse {

	private float httpVersion;
	private String statusCode;
	private String connection;
	private Date date;
	private Date lastModified;
	private String contentType;
	private byte[] content;
	
	public byte[] getBytes() {
		String result = String.format(
				  "HTTP/%f %s\r\n"
				+ "Connection: %s\r\n"
				+ "Server: %s\r\n",
				getHttpVersion(), getStatusCode(),
				getConnection(),
				getServer());
		
		if (getDate() != null) {
			result += String.format("Date: %s\r\n", getDateString(getDate()));
		}
		if (getLastModified() != null) {
			result += String.format("Last-Modified: %s\r\n", getDateString(getLastModified()));
		}
		if (getContent() != null) {
			result += String.format("Content-Length: %d\r\n", getContent().length);
			result += String.format("Content-Type: %s\r\n", getContentType());
		}
		
		result += "\r\n";
		
		byte[] resultBytes;
		
		if (getContent() != null) {		
			resultBytes = new byte[result.length() + getContent().length];
			System.arraycopy(result.getBytes(), 
							 0, 
							 resultBytes, 
							 0, 
							 result.getBytes().length);
			System.arraycopy(getContent(), 
							 0, 
							 resultBytes, 
							 result.getBytes().length, 
							 getContent().length);
		} else {
			resultBytes = result.getBytes();
		}
		
		return resultBytes;
	}
	
	private String getDateString(Date someDate) {
		return DateTimeFormatter.RFC_1123_DATE_TIME.format(
			ZonedDateTime.ofInstant(
				someDate.toInstant(),
				ZoneId.of("GMT")
			)
		);
	}
	
	public float getHttpVersion() {
		return httpVersion;
	}
	public void setHttpVersion(float httpVersion) {
		this.httpVersion = httpVersion;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getServer() {
		return Server.VERSION;
	}
}
