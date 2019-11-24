package io.qvertx.service.context;

public class AuthInfo {

	private AuthScheme scheme;
	
	private String token;

	public AuthInfo() {
	}

	public AuthInfo(AuthScheme scheme, String token) {
		this.scheme = scheme;
		this.token = token;
	}

	public AuthScheme getScheme() {
		return scheme;
	}

	public void setScheme(AuthScheme scheme) {
		this.scheme = scheme;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAuthHeader() {
		return scheme + " "+ token;
	}
	
	@Override
	public String toString() {
		return "AuthSchemeData [scheme=" + scheme + ", token=" + token + "]";
	}

}
