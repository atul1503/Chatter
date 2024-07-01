package com.chatter.Chatter.api.requestbody;

import org.springframework.stereotype.Component;

@Component
public class DoneResponse {
	
	private int status=5;
	private String message="";
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
