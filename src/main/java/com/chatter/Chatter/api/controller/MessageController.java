package com.chatter.Chatter.api.controller;


import com.chatter.Chatter.api.configuration.UserController;
import com.chatter.Chatter.api.models.Content;
import com.chatter.Chatter.api.models.Message;
import com.chatter.Chatter.api.models.Person;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.usertype.internal.OffsetDateTimeCompositeUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chatter.Chatter.api.repository.ContentRespository;
import com.chatter.Chatter.api.repository.MessageRepository;
import com.chatter.Chatter.api.repository.UserRepository;
import com.chatter.Chatter.api.requestbody.DoneResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/messages")
public class MessageController {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MessageRepository mr;
	
	@Autowired
	private UserRepository ur;
	
	@Value("${image.directory}")
	String uploadDir;
	
	
	@Autowired
	private ContentRespository cr;

	
	@GetMapping("/latest")
	public List<Message> getLast5Messages(HttpServletRequest request){
		
		String userone=request.getParameter("userone");
		String usertwo=request.getParameter("usertwo");
		String beforeDateString=request.getParameter("beforeDate");
		String afterDateString=request.getParameter("afterDate");
		
		
		Date afterDate;
		Date beforeDate;
		
		if(beforeDateString == null) {
			beforeDate = new Date(0);
		}
		else {
		  beforeDateString=beforeDateString.replace(" ", "+");
		  beforeDate= Date.from(OffsetDateTime.parse(beforeDateString).toInstant());
		}
		
		if(afterDateString == null) {
			afterDate=new Date();
		}
		else {
			afterDateString=afterDateString.replace(" ", "+");
			afterDate=Date.from(OffsetDateTime.parse(afterDateString).toInstant());
			
		}
		
		return mr.getTopFiveMessages(userone,usertwo,afterDate,beforeDate,PageRequest.of(0,5)).getContent();
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
		String recid=request.getParameter("recid");
		String senderid=request.getParameter("senderid");
		
		Content content=new Content();
		
		content.setPhotourl("None");
		content.setText(text);
		content.setType("text");
		
		//cr.save(content);
		
		Message msg=new Message();
		
		msg.setMsgcontent(content);
		msg.setReceiver(ur.findByUsername(recid));
		msg.setSender(ur.findByUsername(senderid));
		logger.debug(msg.getReceiver().toString());
		msg.setTime(new Date());
		mr.save(msg);
		
		
		DoneResponse dr=new DoneResponse("","Message saved in database.");
		
		msg=null;
		content=null;
		return dr;
				
	}
	
	@PostMapping("/create")
	public DoneResponse createMessageWithImage(@RequestParam("image") MultipartFile file,
			@RequestParam("senderid") String senderid,
			@RequestParam("recid") String recid
			) throws IOException {
		File dir=new File(uploadDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		Message msg=new Message();
		msg.setReceiver(ur.findByUsername(recid));
		msg.setSender(ur.findByUsername(senderid));
		msg.setTime(new Date());
		mr.save(msg);
		Long msgid=msg.getMessageId();
		String extension=file.getOriginalFilename().split("\\.")[1];
		String filename=msgid.toString();
		File dest=new File(dir,filename+"."+extension);
		file.transferTo(dest);
		
		Content content=new Content();
		content.setPhotourl(filename+"."+extension);
		content.setText(file.getOriginalFilename());
		content.setType(extension);
		
		msg.setMsgcontent(content);
		mr.save(msg);
		DoneResponse response=new DoneResponse("","Message saved");
		
		
		
		return response;
		
		
		
		
		
		
	}
	
	
	@GetMapping("/image")
	public ResponseEntity<InputStreamResource> GetImage(HttpServletRequest request) throws IOException {
		Path pathtofile=Paths.get(uploadDir,request.getParameter("image_name"));
		File file=pathtofile.toFile();
		if(!file.exists()) {
			throw new IOException(request.getParameter("image_name")+" doesnot exist.");
		}
		FileSystemResource imgfile=new FileSystemResource(pathtofile);
		
		HttpHeaders headers=new HttpHeaders();
		
		headers.set("Content-Disposition", "inline; filename: \""+imgfile.getFilename()+"\"");
		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(imgfile.getInputStream()));
	}
	
	@GetMapping("/getlatestfromfriends")
	public List<Message> getlatest(HttpServletRequest request) {
		
		String beforeDateString=request.getParameter("beforeDate");
		String afterDateString=request.getParameter("afterDate");
		Date afterDate;
		Date beforeDate;
		
		if(beforeDateString == null) {
			beforeDate = new Date(0);
		}
		else {
		  beforeDateString=beforeDateString.replace(" ", "+");
		  beforeDate= Date.from(OffsetDateTime.parse(beforeDateString).toInstant());
		}
		
		if(afterDateString == null) {
			afterDate=new Date();
		}
		else {
			afterDateString=afterDateString.replace(" ", "+");
			afterDate=Date.from(OffsetDateTime.parse(afterDateString).toInstant());
			
		}
		
		return mr.getLatestMessageFromAllFriends(request.getParameter("username"),afterDate,beforeDate,PageRequest.of(0, 5)).getContent();
		
	}
	
}
