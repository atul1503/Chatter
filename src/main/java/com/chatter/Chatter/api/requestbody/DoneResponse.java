package com.chatter.Chatter.api.requestbody;

import org.springframework.stereotype.Component;


public class DoneResponse {
	
	
	
	public DoneResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}
	private String token;
	private String message="";
	
	
	public String getToken() {
		return this.token;
	}
	public void setToken(String token) {
		this.token=token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
