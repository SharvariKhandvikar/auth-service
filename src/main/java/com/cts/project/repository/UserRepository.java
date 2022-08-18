package com.cts.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.UserLogin;

//This is the user JPA repository
public interface UserRepository extends JpaRepository<UserLogin, String> {

}
