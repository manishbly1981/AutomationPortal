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
	
	@PostMapping(value="/register", produces={"application/json"}, consumes={"application/json"})

	public ResponseEntity<String> registerUser(@RequestBody User user) throws JsonProcessingException {
		return userService.registerUser(user);
	}

	@PostMapping(value="/activateUser", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> activateUser(@RequestParam(name="email") String email, @RequestParam(name="activationCode") String activationCode, @RequestParam(name="password", required = false) String password){
		if (password==null)
			return userService.activateUser(email, activationCode);
		else
			return userService.activateUser(email, activationCode, password);
	}

	@PostMapping(value = "/login", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> login(@RequestParam(name="email") String email, @RequestParam(name="password") String password) {
		return userService.login(email, password);
	}

	@PostMapping(value = "/unlockUser", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> unlockUser(@RequestParam(name="email") String email){
		return userService.unlockUser(email);
	}

	@GetMapping(value = "/admin/users", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<List<User>> getUserList(){
		return ResponseEntity.ok(userService.userList());
	}


//
	@PostMapping(value = "/admin/roles", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> addRoles(@RequestParam(name="email") String email, @RequestParam(name="role")  String role){
		return userService.addRoles(email, role);
	}

	@DeleteMapping(value = "/admin/roles", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> delRoles(@RequestParam(name="email") String email, @RequestParam(name="role")  String role){
		return userService.delRoles(email, role);
	}
	@GetMapping(value = "/admin/roles", produces={"application/json"}, consumes={"application/json"})
	public ResponseEntity<String> getRoles(@RequestParam(name="email", required = false) String email){
		if (email==null)
			return userService.getRoles();
		else
			return userService.getRoles(email);
	}
}
