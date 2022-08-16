package com.cts.project.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CredentialsException.class)
	public ResponseEntity<Object> credentialException(CredentialsException exception) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("timestamp", new Date());
		map.put("status", HttpStatus.UNAUTHORIZED.value());
		map.put("message", "Username and password doesn't match.");
		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
	}

}
