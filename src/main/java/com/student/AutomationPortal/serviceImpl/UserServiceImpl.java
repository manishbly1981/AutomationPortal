package com.student.AutomationPortal.serviceImpl;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	Logger log= Logger.getLogger(this.getClass().getName());

	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	UserRepository userRepository;
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	@Override
	public ResponseEntity<String> registerUser(User user) {
		if (userRepository.findByEmail(user.getEmail())!=null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Email already registered");
			//return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email already registered");
		try{
			user.setActive(false);
			user.setAttempts(0);
//			user.setConfirmationCode(UUID.randomUUID().toString());
			user.setConfirmationCode(CompactServiceImpl.generateConfirmationCode());
			userRepository.save(user);
		}catch(Exception e) {
			log.warning("User registration issue"+ e.getMessage()+ "/n" + e.getStackTrace());
			e.printStackTrace();
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "User not registered");
			
		}
		if (userRepository.findByEmail(user.getEmail())==null)
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "User not registered");
		try {
			sendConfirmationMail(user);
		}catch(Exception e) {
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Confirmation mail not send, please connect with admin");
		}
		return CompactServiceImpl.reportResponse(HttpStatus.CREATED, "User registered successfully");
	}

	@Override
	public ResponseEntity<String> activateUser(String email, String activationCode) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		if (!user.getConfirmationCode().equals(activationCode))
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Activation Code not matched");
		user.setActive(true);
		user.setConfirmationCode("");
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "User Account activated Successfully");
	}
	
	@Override
	public ResponseEntity<String> activateUser(String email, String activationCode, String password) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		if (!user.getConfirmationCode().equals(activationCode))
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Activation Code not matched");
		user.setPassword(password);
		user.setActive(true);
		user.setConfirmationCode("");
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "User Account activated Successfully");
	}
	@Override
	public ResponseEntity<String> login(String email, String password) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "User does not exist");
		if(!user.isActive())
			return CompactServiceImpl.reportResponse(HttpStatus.LOCKED, "User Account is locked");
		if (!user.getPassword().equals(password)) {
			user.setAttempts((user.getAttempts()+1));
			if (user.getAttempts()==3)
				user.setActive(false);
			userRepository.save(user);
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Invalid Password");
		}
		user.setActive(true);
		user.setAttempts(0);
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "User login Successfully");
	}

	
	@Override
	public ResponseEntity<String> unlockUser(String email) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "User does not exist");
		user.setConfirmationCode(CompactServiceImpl.generateConfirmationCode());
		userRepository.save(user);
		sendUnlockMail(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Check your mail to unlock the user");
	}

	

	public void sendConfirmationMail(User user) {
		String mailText="Hello "+ user.getFirstName()+ ",\n You are registered successfully.\n Next time use '"+ user.getConfirmationCode()+ "' confirmation code to access your account";
		String subject= "User registration details";
		emailServiceImpl.sendSimpleEmail(user.getEmail(), subject, mailText);
	}
	
	public void sendUnlockMail(User user) {
		String mailText="Hello "+ user.getFirstName()+ ",\n Your account is locked.\n Next time use '"+ user.getConfirmationCode()+ "' confirmation code to access your account";
		String subject= "User Activation details";
		emailServiceImpl.sendSimpleEmail(user.getEmail(), subject, mailText);
	}
}
