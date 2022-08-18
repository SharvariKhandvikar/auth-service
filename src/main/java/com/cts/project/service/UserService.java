package com.cts.project.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cts.project.controller.AuthenticationController;
import com.cts.project.model.UserLogin;
import com.cts.project.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	UserRepository userRepository;

	//Loads the user 
	@Override
	public UserDetails loadUserByUsername(String username) {
		log.info("BEGIN   -   [loadUserByUsername()]");
		log.debug("Username : {}",username);

		Optional<UserLogin> list = userRepository.findById(username);
		UserLogin userLogin = new UserLogin();
		if(!list.isEmpty()) {
		userLogin = list.get();
		}
		
		if(userLogin == null) {
			throw new NoSuchElementException("No such data present!!!");
		}
		
		log.debug("UserLogin : {}" , userLogin);
		UserDetails user = new User(userLogin.getUserName(), userLogin.getPassword(), new ArrayList<>());
		log.debug("User : {}" , user);

		log.info("END   -   [loadUserByUsername()]");
		return user;
	}

}
