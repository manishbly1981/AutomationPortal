package com.student.AutomationPortal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService= userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@PostMapping("/activateUser")
	public ResponseEntity<String> activateUser(@RequestParam(name="email") String email, @RequestParam(name="activationCode") String activationCode, @RequestParam(name="password", required = false) String password){
		if (password==null)
			return userService.activateUser(email, activationCode);
		else
			return userService.activateUser(email, activationCode, password);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam(name="email") String email, @RequestParam(name="password") String password) {
		return userService.login(email, password);
	}

	@PostMapping("/unlockUser")
	public ResponseEntity<String> unlockUser(@RequestParam(name="email") String email){
		return userService.unlockUser(email);
	}
	
	
}
