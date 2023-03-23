package com.student.AutomationPortal.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.student.AutomationPortal.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserService {
	ResponseEntity<String> registerUser(User user);
	ResponseEntity<String> activateUser(String email, String activationCode);
	ResponseEntity<String> activateUser(String email, String activationCode, String password);
	ResponseEntity<String> unlockUser(String email);
	ResponseEntity<String> login(String email, String password);
	List<User> userList();

	ResponseEntity<String> delRoles(String email, String role);
	ResponseEntity<String> addRoles(String email, String role);
	ResponseEntity<String> getRoles(String email);
	ResponseEntity<String> getRoles();
}
