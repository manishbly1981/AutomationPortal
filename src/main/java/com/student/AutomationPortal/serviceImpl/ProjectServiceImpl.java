package com.student.AutomationPortal.serviceImpl;


import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.Module;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.ModuleRepository;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{
	Logger logger= Logger.getLogger(this.getClass().getName());
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	
	
	@Override
	public ResponseEntity<String> registerProject(Project project) {
		//To do: Need to check if user id is already exist in the system
		if (projectRepository.findByProjectCode(project.getProjectCode())!=null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Project already exist with the same code");
		
		if (projectRepository.findByProjectName(project.getProjectName())!=null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Project already exist with the same name");
		
		/***************************************/
		//Need to write the logic for module
		if (project.getModules()!=null) {
			List<Module> modules= new ArrayList<>();
			for(Module module:project.getModules()) {
				Module repoModule= moduleRepository.findByName(module.getName());
				if (repoModule==null)
					modules.add(module);
				else
					modules.add(repoModule);
				
			}
			project.setModules(modules);
		}
		/***************************************/
		projectRepository.save(project);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Project is registered");
	}

	@Override
	public ResponseEntity<String> allocateProject(String email, String projectCode) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();

		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " Project is not registered");	

		//Check if project is already not assigned
		if (user.getProjects().contains(project))
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, project.getProjectCode() + " is already allocated to user "+ user.getFirstName() + " " + user.getLastName());
		
		Project projectByCode = projectRepository.findByProjectCode(projectCode);
		Project projectByName = projectRepository.findByProjectName(projectCode);
		Set<Project> projects= user.getProjects();
		if (projectByCode!=null) {
			projects.add(projectByCode);
		}
		else if (projectByName!=null) {
			projects.add(projectByName);
		}
		else {
			projects.add(project);
		}
		user.setProjects(projects);
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, project.getProjectCode() + " Project is allocated to user "+ user.getFirstName() + " " + user.getLastName());
	}

	@Override
	public ResponseEntity<String> deAllocateProject(String email, String projectCode) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project is not registered");	
		
		//Check if project is not allocated to user
		if (!user.getProjects().contains(project))
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, project.getProjectCode() + " is is not allocated to user "+ user.getFirstName() + " " + user.getLastName());
		
		//Deallocated project
		Set<Project> projects=user.getProjects();
		user.getProjects().remove(project);
		user.setProjects(projects);
		userRepository.save(user);
			return CompactServiceImpl.reportResponse(HttpStatus.OK, projectCode+ " Project deallocated  to user "+ user.getFirstName() + " " + user.getLastName());
	}

	@Override
	public ResponseEntity<String> getAssignedProjects(String email) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();

		Set<Project> projects= user.getProjects();
		if (projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project is assigned to user");
	
		return CompactServiceImpl.reportResponse(HttpStatus.OK, projects);
	}

	@Override
	public ResponseEntity<String> getAllProjects() {
		List<Project> projectList= projectRepository.findAll();
		Set<Project> projects= new HashSet<>(projectList);
		if (projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project is assigned to user");
	
		return CompactServiceImpl.reportResponse(HttpStatus.OK, projects);
	}

	//Method to be used across the framework internally
	public Set<Project> getProject(String email, String projectCode) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return null;
		
		Set<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toSet());
		if(matchingProject==null||matchingProject.size()<=0)
			return null;
		else
			return matchingProject;
	}

	@Override
	public ResponseEntity<String> deleteProject(String email, String projectCode) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project is not registered");	
		
		//Check if project is not allocated to user
		if (!user.getProjects().contains(project))
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, project.getProjectCode() + " is is not allocated to user "+ user.getFirstName() + " " + user.getLastName());
		
		//Deallocated project
		Set<Project> projects=user.getProjects();
		user.getProjects().remove(project);
		user.setProjects(projects);
		userRepository.save(user);
		projectRepository.delete(project);
		return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, projectCode+ " Project deallocated  to user "+ user.getFirstName() + " " + user.getLastName());
	}
}

