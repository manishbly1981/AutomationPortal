package com.student.AutomationPortal.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService= userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) throws JsonProcessingException {
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

	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getUserList(){
		return ResponseEntity.ok(userService.userList());
	}


//
	@PostMapping("/admin/roles")
	public ResponseEntity<String> addRoles(@RequestParam(name="email") String email, @RequestParam(name="role")  String role){
		return userService.addRoles(email, role);
	}

	@DeleteMapping("/admin/roles")
	public ResponseEntity<String> delRoles(@RequestParam(name="email") String email, @RequestParam(name="role")  String role){
		return userService.delRoles(email, role);
	}
	@GetMapping("/admin/roles")
	public ResponseEntity<String> getRoles(@RequestParam(name="email", required = false) String email){
		if (email==null)
			return userService.getRoles();
		else
			return userService.getRoles(email);
	}
}
