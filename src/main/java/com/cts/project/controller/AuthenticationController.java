package com.cts.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.exception.CredentialsException;
import com.cts.project.model.Token;
import com.cts.project.model.UserLogin;
import com.cts.project.service.JwtUtil;
import com.cts.project.service.UserService;

@RestController
//@Api(value = "Endpoints for Authentication Service")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

	Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	//@ApiOperation(value = "userLogin", notes = "tokes user credentials and generate a JWT", httpMethod = "POST", response = ResponseEntity.class)
	public Token login(
			//@ApiParam(name = "userloginCredentials", value = "Login credentials of the User.") 
			@RequestBody UserLogin userLoginCredentials)
			throws CredentialsException {
		
		log.info("BEGIN   -   [login(userLoginCredentials)]");
		final UserDetails userdetails = userService.loadUserByUsername(userLoginCredentials.getUserName());
		log.debug("{}", userdetails);
		if (userdetails.getPassword().equals(userLoginCredentials.getPassword())) {
			log.info("END  -   [login(userLoginCredentials)]");		
			return  new Token(jwtUtil.generateToken(userdetails));
		} else {
			log.info("END  -   [login(userLoginCredentials)]");
			throw new CredentialsException("Invalid Username or password");
		}

	}

	@GetMapping("/validate")
	//@ApiOperation(value = "tokenValidation", notes = "returns boolean after validating JWT", httpMethod = "GET", response = ResponseEntity.class)
	public 	boolean getValidity(
			//@ApiParam(name = "token", value = "JWT for current user") 
			@RequestHeader(name = "Authorization") String token) {
		log.info("BEGIN   -   [getValidty(token)]");
		
			if(token.equals("")) {
				return false;
			}
			else {
				try {
			UserDetails user = userService.loadUserByUsername(jwtUtil.extractUsername(token));
			jwtUtil.validateToken(token, user);
			log.debug("Valid token");
			log.info("{END} : validate()");
			return true;
				}
				catch(Exception e) {
					log.debug("Invalid token");
					log.info("{END} : getValidity()");
					return false;	
				}
			}
	}

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

}
