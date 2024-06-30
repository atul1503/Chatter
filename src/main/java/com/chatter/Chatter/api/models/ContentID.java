package com.chatter.Chatter.api.models;

import java.io.Serializable;
import java.util.Objects;

public class ContentID implements Serializable {
	
	private String text;
	private String photourl;
	
	
	
	
	public ContentID() {
	}
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
	@Override
	public int hashCode() {
		return Objects.hash(photourl, text);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContentID other = (ContentID) obj;
		return Objects.equals(photourl, other.photourl) && Objects.equals(text, other.text);
	}
	
	

}
