package com.chatter.Chatter.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatter.Chatter.api.models.Person;
import com.chatter.Chatter.api.repository.UserRepository;

@Service
public class PersonService implements UserDetailsService {
	
	@Autowired
	private UserRepository ur;

	@Override
	public Person loadUserByUsername(String username) throws UsernameNotFoundException {
		Person user = ur.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
	}
	
	

}
