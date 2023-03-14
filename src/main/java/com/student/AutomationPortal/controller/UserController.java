package com.student.AutomationPortal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.student.AutomationPortal.dto.RawData;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	private UserService userService;

	@Autowired
	private RawData rawData;
	
	public UserController(UserService userService) {
		this.userService= userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) throws JsonProcessingException {
		/*
	 	ResponseEntity<String> dummyResponse= userService.registerUser(user);
		 
		Map<String, Object> object = new HashMap<>();
		  object.put("msg", dummyResponse.getBody());

		  ObjectMapper mapper = new ObjectMapper();
		  String response= mapper.writeValueAsString(dummyResponse);
		return ResponseEntity.status(dummyResponse.getStatusCode()).body(response);
		*/
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
	
	@PostMapping("/testData")
	public ResponseEntity<String> testDataGenerator(){
		rawData.RawDataGenerator();
		return ResponseEntity.ok("Test Data Generated");
	}
	
	@GetMapping("/auth/users")
	public ResponseEntity<List<User>> getUserList(){
		return ResponseEntity.ok(userService.userList());
	}
}
