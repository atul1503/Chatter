package com.chatter.Chatter.api.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.chatter.Chatter.api.controller.UserController;

import java.util.function.Supplier;

@Component
public class AllowAllAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	
	private static final Logger logger=LoggerFactory.getLogger(AllowAllAuthorizationManager.class);

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
    	Authentication auth = authentication.get();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        return new AuthorizationDecision(isAuthenticated);
    }
}