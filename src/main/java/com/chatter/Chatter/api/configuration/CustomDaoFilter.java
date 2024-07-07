package com.chatter.Chatter.api.configuration;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.chatter.Chatter.api.controller.UserController;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomDaoFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);

    public CustomDaoFilter(AuthenticationManager authManager) {
        super(authManager);
        setFilterProcessesUrl("/users/login"); // Set the URL this filter should process
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getHeader("password");
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getHeader("username");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
    	SecurityContextHolder.getContext().setAuthentication(authResult);
    	HttpSession session = request.getSession(true); 
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        
        logger.debug("Authentication successful: {}", authResult);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Authentication successful");
        
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed");
    }
}