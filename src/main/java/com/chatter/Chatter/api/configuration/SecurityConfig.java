package com.chatter.Chatter.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chatter.Chatter.api.models.Person;
import com.chatter.Chatter.api.service.PersonService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PersonService service;
	
	@Autowired
	private AllowAllAuthorizationManager am;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    	.authorizeHttpRequests(auth -> auth
    	    .requestMatchers("/users/register").permitAll()
    	    .requestMatchers("/users/login").permitAll()
    	    .anyRequest().access(am)
    	)
    	.csrf(csrf -> csrf.disable())
    	.addFilterAt(customdaofilter(configuauthmanager(service,getPasswordEncoder())), UsernamePasswordAuthenticationFilter.class)
    	.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            );

    	return http.build();
    }
    
    
    @Bean 
    public AuthenticationManager configuauthmanager(PersonService ps,PasswordEncoder pe) {
    	
    DaoAuthenticationProvider ap=new DaoAuthenticationProvider();
    ap.setUserDetailsService(service);
    ap.setPasswordEncoder(getPasswordEncoder());
    
    return new ProviderManager(ap);
    	
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CustomDaoFilter customdaofilter(AuthenticationManager authmanager) {
    	return new CustomDaoFilter(authmanager);
    }
    
    
    
}