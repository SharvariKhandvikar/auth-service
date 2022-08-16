package com.cts.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.project.controller.AuthenticationController;
import com.cts.project.exception.CredentialsException;
import com.cts.project.exception.GlobalExceptionHandler;
import com.cts.project.model.Token;
import com.cts.project.model.UserLogin;

@SpringBootTest
class AuthenticationserviceApplicationTests {
	
	Logger log = LoggerFactory.getLogger(AuthenticationserviceApplicationTests.class);
	
	@Autowired
	AuthenticationController controller;
	
	@Autowired
	GlobalExceptionHandler exceptionHandler;

	@Test
	void applicationStarts() throws IOException {
		AuthenticationserviceApplication.main(new String[] {});
		assertTrue(true);
	}
	
	@Test
	void testHealthcheck() {
		ResponseEntity<String> actual = controller.healthCheck();
		ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
		
	}
	
	@Test
	void testValidityOfToken() throws CredentialsException {
		Token token = controller.login(new UserLogin("user1", "user1"));
		boolean actual = controller.getValidity(token.getToken());
		assertTrue(actual);
	}
	
	@Test
	void testWrongToken() {
		
		boolean actual = controller.getValidity("wrongToken");
		assertFalse(actual);
		
	}
	
	@Test
	void testEmptyToken() {
		
		boolean actual = controller.getValidity("");
		assertFalse(actual);
		
	}
	
	@Test
	void testAddedToken() throws CredentialsException {
		Token token = controller.login(new UserLogin("user1", "user1"));
		boolean actual = controller.getValidity(token.getToken()+"bfdv");
		assertFalse(actual);
		
	}
	
	@Test
	void testCredentialException() throws CredentialsException {
		CredentialsException thrown = assertThrows(CredentialsException.class, 
				() -> controller.login(new UserLogin("user1", "wrongpass")), 
				"Exception did not match!!!");
		ResponseEntity<Object> actual = exceptionHandler.credentialException(thrown);
		
		log.info("Message: "+thrown.getMessage());
		
		log.info("Response entity is   "+actual.toString());
		
		assertTrue(actual.getStatusCode().equals(HttpStatus.UNAUTHORIZED));

	}
	
	@Test
	void testSetMethods() {
		
		Token t = new Token();
		t.setToken("anytesttoken");
		UserLogin ul = new UserLogin();
		ul.setUserName("testuser");
		ul.setPassword("pass");
		assertTrue(true);
	}

}
