package com.chatter.Chatter.api.configuration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.chatter.Chatter.api.repository.UserRepository;
import com.chatter.Chatter.api.service.PersonService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtService {
	
	
	@Autowired
	private PersonService service;
	
	private String key="om namah shivaya , om namah shivaya , om namh shivaya";
	private int expirationTime=86400;

	String generateToken(UserDetails user) {
		
		return Jwts.builder()
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis()+expirationTime))
		.setSubject(user.getUsername())
		.signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)
		.compact();
		
	}
	
	UserDetails getUser(String token) {
		
		String username=Jwts.parserBuilder()
		.setSigningKey(key.getBytes())
		.build()
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
		
		return service.loadUserByUsername(username);
		
	}
	
	
	boolean verifyToken(String token) throws SignatureException,ExpiredJwtException {
		
		return Jwts.parserBuilder()
		.setSigningKey(key.getBytes())
		.build()
		.parseClaimsJws(token)
		.getBody()
		.getExpiration().before(new Date());
		
	}
}
