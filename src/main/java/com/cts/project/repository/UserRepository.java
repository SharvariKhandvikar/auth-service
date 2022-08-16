package com.cts.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.project.model.UserLogin;

public interface UserRepository extends JpaRepository<UserLogin, String> {

}
