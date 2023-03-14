package com.student.AutomationPortal.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.student.AutomationPortal.model.User;

public interface UserService {
	ResponseEntity<String> registerUser(User user);
	ResponseEntity<String> activateUser(String email, String activationCode);
	ResponseEntity<String> activateUser(String email, String activationCode, String password);
	ResponseEntity<String> unlockUser(String email);
	ResponseEntity<String> login(String email, String password);
	List<User> userList();
	
}
