package com.chatter.Chatter.api.configuration;


import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class AllowAllAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
    	Authentication auth = authentication.get();
        boolean isAuthenticated = auth != null && auth.isAuthenticated();
        return new AuthorizationDecision(isAuthenticated);
    }
}