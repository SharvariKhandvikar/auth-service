package com.cts.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
//@ApiModel(description = "Model class for User Login")
public class UserLogin {

	@Id
	@ApiModelProperty(value = "Username of the Pensioner")
	private String userName;

	@ApiModelProperty(value = "Password of the Pensioner")
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
		super();
	}

	@Override
	public String toString() {
		return "UserLogin [userName=" + userName + ", password=" + password + "]";
	}
	
	

}
