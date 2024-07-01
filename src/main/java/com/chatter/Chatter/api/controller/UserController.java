package com.chatter.Chatter.api.controller;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatter.Chatter.api.requestbody.DoneResponse;
import com.chatter.Chatter.api.requestbody.Identify;

import com.chatter.Chatter.api.models.Person;
import com.chatter.Chatter.api.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/users")
public class UserController {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	public UserRepository ur;
	
	@Autowired
	public Person p;
	
	@Autowired
	DoneResponse response;
	
	@PostMapping("/login")
	public DoneResponse checkLogin(@RequestBody Identify body) {
		
		String username=body.getUsername();
		String password=body.getPassword();
		
	
		p.setPassword(password);
		p.setUsername(username);
		
		Person found=ur.findByUsernameAndPassword(username, password);
		
		if(found != null ) {
			response.setMessage("Login success.");
			response.setStatus(200);
			return response;
		}
		response.setMessage("Login failed. The username or password is not correct.");
		response.setStatus(305);
		return response;
	}
	
	@PostMapping("/register")
	public DoneResponse register(@RequestBody Identify body) {
		
		String username=body.getUsername();
		String password=body.getPassword();
		
		Optional<Person> found=ur.findById(username);
		
		if(!found.isPresent()) {
			p.setPassword(password);
			p.setUsername(username);
			ur.save(p);
			response.setMessage("User added.");
			response.setStatus(200);
			return response;
			
		}
		response.setMessage("User already present.");
		response.setStatus(305);
		return response;
		
	}

}
