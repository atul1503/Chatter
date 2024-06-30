package com.chatter.Chatter.api.models;

import jakarta.persistence.Id;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;

@Entity
@Component
public class Person {

	
	public Person() {
	}

	@Id
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
