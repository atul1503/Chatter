package com.chatter.Chatter.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.chatter.Chatter.api.models.Person;


public interface UserRepository extends JpaRepository<Person,String> {
	
	
	public Person findByUsernameAndPassword(String username,String password);
	
	public Person findByUsername(String Username);
}
