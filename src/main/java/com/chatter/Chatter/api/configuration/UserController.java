package com.chatter.Chatter.api.configuration;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private UserRepository ur;
	
	
	@Autowired
	private PasswordEncoder pe;
	

	@Autowired
	private JwtService service;
	
	
	@PostMapping("/register")
	public ResponseEntity<DoneResponse> register(@RequestBody Identify body) {
		
		Person newUser=new Person();
		newUser.setUsername(body.getUsername());
		newUser.setPassword(pe.encode(body.getPassword()));
		try {
			ur.save(newUser);
		}
		catch (Exception e) {
			return ResponseEntity.ok(new DoneResponse("","User already exists."));
		}
		
		String token=service.generateToken(newUser);
		
		DoneResponse responseBody=new DoneResponse(token,"User is registered. Please find the jwt token.");
		
		
		return ResponseEntity.ok(responseBody);
		
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<DoneResponse> login(@RequestBody Identify body){
		
		Person user=ur.getReferenceById(body.getUsername());
		if(!pe.matches(body.getPassword(), user.getPassword())) {
			return ResponseEntity.status(300).body(new DoneResponse("","User details are incorrect")); 
		}
		
		String token=service.generateToken(user);
		
		
		return ResponseEntity.ok(new DoneResponse(token, "login success. Token is generated."));
		
		
	}

}
