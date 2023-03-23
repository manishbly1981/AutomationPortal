package com.student.AutomationPortal.serviceImpl;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.Role;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.RoleRepository;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	Logger log= Logger.getLogger(this.getClass().getName());

	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	UserRepository userRepository;
	RoleRepository roleRepository;
	ProjectRepository projectRepository;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository) {
		this.userRepository= userRepository;
		this.roleRepository= roleRepository;
		this.projectRepository= projectRepository;
	}
	@Override
	public ResponseEntity<String> registerUser(User user) {
		Optional<User> oUser = userRepository.findByEmail(user.getEmail());
		if(oUser.isPresent())
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email already registered");
		try{
			user.setActive(false);
			user.setAttempts(0);
//			user.setConfirmationCode(UUID.randomUUID().toString());
			user.setConfirmationCode(CompactServiceImpl.generateConfirmationCode());
			/****************************************************/
			if (user.getRoles()!=null) {
				Set<Role> roles= new HashSet<>();
				for(Role role:user.getRoles()) {
					Role currentRole= roleRepository.findByRole(role.getRole());
					if (currentRole!=null)
						roles.add(currentRole);
					else
						roles.add(role);
				}
				user.setRoles(roles);
			}
			if (user.getProjects()!=null) {
				Set<Project> projects= new HashSet<>();
				for(Project currentProject:user.getProjects()) {
					Project repoProjectCode=projectRepository.findByProjectCode(currentProject.getProjectCode());
					Project repoProjectName=projectRepository.findByProjectName(currentProject.getProjectName());
					if(repoProjectCode!=null)
						projects.add(repoProjectCode);
					else if(repoProjectName!=null)
						projects.add(repoProjectName);
					else
						projects.add(currentProject);
				}
				user.setProjects(projects);
			}
			/****************************************************/
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
//		User user= userRepository.findByEmail(email);
//		if (user==null)
//			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email is not registered");
		User user= oUser.get();
		if (user.getConfirmationCode().equals(""))
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "User Account is already activated");
		if (!user.getConfirmationCode().equals(activationCode))
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Activation Code not matched");
		user.setActive(true);
		user.setConfirmationCode("");
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "User Account activated Successfully");
	}
	
	@Override
	public ResponseEntity<String> activateUser(String email, String activationCode, String password) {
//		User user= userRepository.findByEmail(email);
//		if (user==null)
//			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email is not registered");
		User user= oUser.get();
		if (user.getConfirmationCode().equals(""))
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "User Account is already activated");
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
//		User user= userRepository.findByEmail(email);
//		if (user==null)
//			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "User does not exist");
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email is not registered");
		User user= oUser.get();
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
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Email is not registered");
		User user= oUser.get();
		user.setConfirmationCode(CompactServiceImpl.generateConfirmationCode());
		userRepository.save(user);
		sendUnlockMail(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Check your mail to unlock the user");
	}

	public List<User> userList(){
		List<User> userList= userRepository.findAll();
		return userList;
		//return CompactServiceImpl.reportResponse(HttpStatus.OK, userList);
	}

	@Override
	public ResponseEntity<String> delRoles(String email, String role) {
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email not registered");
		User user= oUser.get();


		Role oRole= roleRepository.findByRole(role);
		if(oRole==null){
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Please check the role");
		}
		Set<Role> roles = user.getRoles();
		if (!roles.contains(oRole)) {
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "User does not have role "+ role);
		}
		roles.remove(oRole);
		user.setRoles(roles);
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Role deregistered from user");


	}

	@Override
	public ResponseEntity<String> addRoles(String email, String role) {
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return CompactServiceImpl.reportResponse(HttpStatus.OK, "Email not registered");
		User user= oUser.get();

		Role oRole= roleRepository.findByRole(role);
		if(oRole==null){
			Role newRole=new Role();
			newRole.setRole(role);
			roleRepository.save(newRole);
			oRole= roleRepository.findByRole(role);
		}
		Set<Role> roles = user.getRoles();
		if (roles.contains(oRole))
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Role aready assigned to user");
		roles.add(oRole);
		user.setRoles(roles);
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Role assigned to user");
	}

	@Override
	public ResponseEntity<String> getRoles(String email) {
		Optional<User> oUser = userRepository.findByEmail(email);
		if(!oUser.isPresent())
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email not registered");
		User user= oUser.get();
		return CompactServiceImpl.reportResponse(HttpStatus.OK, user.getRoles());
	}

	@Override
	public ResponseEntity<String> getRoles() {
		return CompactServiceImpl.reportResponse(HttpStatus.OK, roleRepository.findAll());
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
