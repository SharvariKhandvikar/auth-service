package com.cts.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

//This is the entity class for the user with username and password
@Entity
public class UserLogin {

	@Id
	private String userName;
	private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserLogin(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public UserLogin() {
	}
	
	

}
