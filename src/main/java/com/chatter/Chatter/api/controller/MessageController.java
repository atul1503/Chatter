package com.chatter.Chatter.api.controller;


import com.chatter.Chatter.api.models.Content;
import com.chatter.Chatter.api.models.Message;
import com.chatter.Chatter.api.models.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatter.Chatter.api.repository.ContentRespository;
import com.chatter.Chatter.api.repository.MessageRepository;
import com.chatter.Chatter.api.repository.UserRepository;
import com.chatter.Chatter.api.requestbody.DoneResponse;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/messages")
public class MessageController {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MessageRepository mr;
	
	@Autowired
	private UserRepository ur;
	
	
	@Autowired
	private ContentRespository cr;

	
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
			SimpleDateFormat formatter=new SimpleDateFormat();
			lastDate=formatter.parse(datestring);
			formatter=null;
		}
		catch(ParseException e) {
			lastDate=null;
		}
		return mr.getOldMessages(senderid,receiverid,lastDate,PageRequest.of(0, 5)).getContent();
	}
	
	
	@PostMapping("/createSimple")
	public DoneResponse createMessageSimple(HttpServletRequest request) {
		String text=request.getParameter("text");
		String recid=request.getParameter("receiverid");
		String senderid=request.getParameter("senderid");
		
		Content content=new Content();
		
		content.setPhotourl("None");
		content.setText(text);
		content.setType("text");
		
		cr.save(content);
		
		Message msg=new Message();
		
		msg.setMsgcontent(content);
		msg.setReceiver(ur.findByUsername(recid));
		msg.setSender(ur.findByUsername(senderid));
		msg.setTime(new Date());
		mr.save(msg);
		
		DoneResponse dr=new DoneResponse();
		dr.setMessage("Message saved in database.");
		dr.setStatus(200);
		
		msg=null;
		return dr;
				
	}
	
	
}
