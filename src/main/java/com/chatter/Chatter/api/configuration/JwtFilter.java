package com.chatter.Chatter.api.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private JwtService service;

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if( request.getHeader("Authorization")==null) {
			
			doFilter(request, response, filterChain);
			return;
			
		}
		String token=request.getHeader("Authorization").split("Bearer ")[1];
		
		try{
			service.verifyToken(token);
		}
		catch (ExpiredJwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token has expired");
		}
		
		UserDetails user=service.getUser(token);
		
		UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		doFilter(request, response, filterChain);
	}

}
