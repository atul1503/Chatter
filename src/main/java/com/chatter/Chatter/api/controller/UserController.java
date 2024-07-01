package com.chatter.Chatter.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chatter.Chatter.api.requestbody.Identify;

import com.chatter.Chatter.api.models.Person;
import com.chatter.Chatter.api.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/users")
public class UserController {
	
	
	@Autowired
	public UserRepository ur;
	
	@Autowired
	public Person p;
	
	@PostMapping("/login")
	public Boolean checkLogin(@RequestBody Identify body) {
		
		String username=body.getUsername();
		String password=body.getPassword();
		
	
		p.setPassword(password);
		p.setUsername(username);
		
		Person found=ur.findByUsernameAndPassword(username, password);
		
		if(found != null ) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/register")
	public Boolean register(@RequestBody Identify body) {
		
		String username=body.getUsername();
		String password=body.getPassword();
		
		Optional<Person> found=ur.findById(username);
		if(found == null) {
			p.setPassword(password);
			p.setUsername(username);
			ur.save(p);
			return true;
		}
		return false;
		
	}

}
