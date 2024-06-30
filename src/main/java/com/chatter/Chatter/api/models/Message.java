package com.chatter.Chatter.api.models;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Message")
@Component
public class Message {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long messageId;
	
	@Column(nullable=false)
	private Date time;
	
	
	@ManyToOne
	private Person receiver;
	
	@ManyToOne
	private Person sender;
	
	
	public Message() {
	}


	@OneToOne(mappedBy = "message")
	private Content msgcontent;


	public Long getMessageId() {
		return messageId;
	}


	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public Person getReceiver() {
		return receiver;
	}


	public void setReceiver(Person receiver) {
		this.receiver = receiver;
	}


	public Person getSender() {
		return sender;
	}


	public void setSender(Person sender) {
		this.sender = sender;
	}


	public Content getMsgcontent() {
		return msgcontent;
	}


	public void setMsgcontent(Content msgcontent) {
		this.msgcontent = msgcontent;
	}
	
	
	
	
}
