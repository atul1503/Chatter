package com.chatter.Chatter.api.configuration;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfiguration {

	
	@Bean
	public SimpleDateFormat sdf() {
		return new SimpleDateFormat();
	}
}
