package com.chatter.Chatter.api.controller;


import com.chatter.Chatter.api.models.Content;
import com.chatter.Chatter.api.models.Message;
import com.chatter.Chatter.api.models.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chatter.Chatter.api.repository.MessageRepository;
import com.chatter.Chatter.api.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageRepository mr;
	
	
	@Autowired
	private Message msg;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private Content content;
	
	@Autowired
	private SimpleDateFormat formatter;
	
	@GetMapping("/latest")
	public List<Message> getLast5Messages(@RequestParam("senderid") String senderid,
			@RequestParam("receiverid") String receiverid){
		return mr.getTopFiveMessages(senderid,receiverid,PageRequest.of(0,5)).getContent();
	}
	
	
	@GetMapping("/past")
	public List<Message> getPastMessages(@RequestParam("senderid") String senderid,
			@RequestParam("receiverid") String receiverid, @RequestParam("lastDate") String datestring){
		Date lastDate;
		try{
			lastDate=formatter.parse(datestring);
		}
		catch(ParseException e) {
			lastDate=null;
		}
		return mr.getOldMessages(senderid,receiverid,lastDate,PageRequest.of(0, 5)).getContent();
	}
	
	
	@PostMapping("/createSimple")
	public boolean createMessageSimple(HttpServletRequest request) {
		String username=request.getParameter("username");
		String text=request.getParameter("text");
		String recid=request.getParameter("receiverid");
		String senderid=request.getParameter("senderid");
		
		content.setPhotourl("None");
		content.setText(text);
		content.setType("text");
		
		msg.setMsgcontent(content);
		msg.setReceiver(ur.findByUsername(recid));
		msg.setSender(ur.findByUsername(senderid));
		mr.save(msg);
		return true;
	}
	
	
}
