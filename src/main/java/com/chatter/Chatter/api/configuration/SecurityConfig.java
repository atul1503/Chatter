package com.chatter.Chatter.api.configuration;

import org.springframework.web.filter.CorsFilter;
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
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.chatter.Chatter.api.models.Person;
import com.chatter.Chatter.api.service.PersonService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PersonService service;

	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
    	.authorizeHttpRequests(auth -> auth
    	    .requestMatchers("/users/register").permitAll()
    	    .requestMatchers("/users/login").permitAll()
    	    .anyRequest().authenticated()
    	)
    	.csrf(csrf -> csrf.disable())
    	.addFilterAt(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
    	.addFilterAt(customCorsFilter(), CorsFilter.class);
      	return http.build();
    }
    
    
    @Bean
    public CorsFilter customCorsFilter() {
    	
    		CorsConfiguration config=new CorsConfiguration();
    		config.setAllowCredentials(true);
    		config.addAllowedOriginPattern("*");
    		config.addAllowedHeader("*");
    		config.addAllowedMethod("*");
    		
    		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
    		source.registerCorsConfiguration("/**", config);
    		return new CorsFilter(source);
 
    }
    
    @Bean
    public JwtFilter jwtFilter() {
    	return new JwtFilter();
    }
    
    @Bean
    public BCryptPasswordEncoder getEncoder() {
    	return new BCryptPasswordEncoder();
    }
     
}