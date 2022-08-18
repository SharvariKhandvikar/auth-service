package com.cts.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cts.project.controller.AuthenticationController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@Value("${app.secret}")
	private String secretKey;

	@Value("${app.expiry}")
	private int expirationTime;

	//Getting username from the token
	public String extractUsername(String token) {
		log.info("BEGIN   -   [extractUsername(token)]");
		String userName = extractClaim(token, Claims::getSubject);
		log.debug("Username{}" , userName);
		log.info("END   -   [extractUsername(token)]");
		return userName;
	}

	//Extracting expiry time of token
	public Date extractExpiration(String token) {
		log.info("BEGIN   -   [extractExpiration(token)]");
		Date date = extractClaim(token, Claims::getExpiration);
		log.debug("Date{}" , date);
		log.info("END   -   [extractUsername(token)]");
		return date;
	}

	//Extract claims section
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		log.info("BEGIN   -   [extractClaims()]");
		final Claims claims = extractAllClaims(token);
		log.debug("Claims{}" , claims);
		log.info("END   -   [extractClaims()]");
		return claimsResolver.apply(claims);
	}

	//Extract claims
	private Claims extractAllClaims(String token) {
		log.info("BEGIN   -   [extractAllClaims(token)]");
		Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		log.debug("Claims{}" , claim);
		log.info("END   -   [extractAllClaims()]");
		return claim;
	}

	//Check if token expried
	private Boolean isTokenExpired(String token) {
		log.info("BEGIN   -   [isTokenExpired(token)]");
		boolean bool = extractExpiration(token).before(new Date());
		log.debug("Boolean{}" , bool);
		log.info("END   -   [isTokenExpired(token)]");
		return bool;
	}

	//Generate the token
	public String generateToken(UserDetails userDetails) {
		log.info("BEGIN   -   [generateToken(userDetails)]");
		Map<String, Object> claims = new HashMap<>();
		log.debug("CLaims{}" , claims);
		String token = createToken(claims, userDetails.getUsername());
		log.debug("Token{}" , token);
		log.info("END   -   [generateToken(userDetails)]");
		return token;
	}

	//Create the token using cliams
	private String createToken(Map<String, Object> claims, String subject) {
		log.info("BEGIN   -   [createToken()]");
		String token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		log.debug("Token {}" , token);
		log.info("END   -   [createToken()]");
		return token;
	}

	//Validate the token
	public Boolean validateToken(String token, UserDetails userDetails) {
		log.info("BEGIN   -   [validateToken(token,userDetails)]");
		final String username = extractUsername(token);
		log.debug("Username {}" , username);
		boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		log.debug("isValid{}" , isValid);
		log.info("END   -   [validateToken(token,userDetails)]");
		return isValid;
	}

}
