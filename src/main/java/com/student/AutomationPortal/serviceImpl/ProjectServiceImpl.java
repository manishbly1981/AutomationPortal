package com.student.AutomationPortal.serviceImpl;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.ProjectService;

public class ProjectServiceImpl implements ProjectService{
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;
	
	Logger logger= Logger.getLogger(this.getClass().getName());
	
	@Override
	public ResponseEntity<String> registerProject(Project project) {
		//To do: Need to check if user id is already exist in the system
		if (projectRepository.findByProjectCode(project.getProjectCode())!=null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Project already exist with the same code");
		
		if (projectRepository.findByProjectName(project.getProjectName())!=null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Project already exist with the same name");
		
		projectRepository.save(project);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, "Project is registered");
	}

	@Override
	public ResponseEntity<String> allocateProject(String email, String projectCode) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project is not registered");	
		
			return CompactServiceImpl.reportResponse(HttpStatus.FAILED_DEPENDENCY, "Need to implement user allocation to the project");
	}

	@Override
	public ResponseEntity<String> deAllocateProject(String email, String projectCode) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Project is not registered");	
		
			return CompactServiceImpl.reportResponse(HttpStatus.FAILED_DEPENDENCY, "Need to implement user de-allocation to the project");
	}

	@Override
	public ResponseEntity<String> getAssignedProjects(String email) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		
//			List<Project> projects = userRepository.findAssignedProjects(email);
//			return CompactServiceImpl.reportResponse(HttpStatus.OK, projects.toString());
		return CompactServiceImpl.reportResponse(HttpStatus.FAILED_DEPENDENCY, "Need to write logic to get project list");
	}

}

