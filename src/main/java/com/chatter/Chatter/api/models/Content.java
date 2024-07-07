package com.chatter.Chatter.api.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@Component
@IdClass(ContentID.class)
public class Content {
	
	
	@Id
	@Column(name="text")
	private String text;
	
	@Id
	@Column(name = "photourl")
	private String photourl;

	public Content() {}

	private String type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Message is "+text+" and url is"+photourl+" and type is "+type;
	}
	
	
		
}
