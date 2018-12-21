package cn.edu.scu.raizerugu;

import java.time.ZonedDateTime;

import com.alibaba.fastjson.JSON;

public class HTTPRequest {
	
	private HTTPMethod method;
	private String path;
	private float httpVersion;
	private String host;
	private String connection;
	private String userAgent;
	private String acceptLanguage;
	private ZonedDateTime ifModifiedSince;
	
	public boolean hasIfModifiedSinceHeader() {
		return ifModifiedSince != null;
	}
	
	public ZonedDateTime getIfModifiedSince() {
		return ifModifiedSince;
	}

	public void setIfModifiedSince(ZonedDateTime ifModifiedSince) {
		this.ifModifiedSince = ifModifiedSince;
	}

	public HTTPMethod getMethod() {
		return method;
	}

	public void setMethod(HTTPMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public float getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(float httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
