package com.student.AutomationPortal.serviceImpl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
			Set<Module> modules= new HashSet<>();
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
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		Project project= projectRepository.findByProjectCode(projectCode);
		if (project==null)
			project= projectRepository.findByProjectName(projectCode);
			if(project==null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " Project is not registered");	

		//Check if project is already not assigned
		if (user.getProjects().contains(project))
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, project.getProjectCode() + " is already allocated to user "+ user.getFirstName() + " " + user.getLastName());

		Set<Project> projects= new HashSet<>();
		projects.add(project);
		user.setProjects(projects);
		
		userRepository.save(user);
		return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, project.getProjectCode() + " Project is allocated to user "+ user.getFirstName() + " " + user.getLastName());
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
		
		//Check if project is not allocated to user
		if (!user.getProjects().contains(project))
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, project.getProjectCode() + " is is not allocated to user "+ user.getFirstName() + " " + user.getLastName());
		
		//Deallocated project
		Set<Project> projects=user.getProjects();
		user.getProjects().remove(project);
		user.setProjects(projects);
		userRepository.save(user);
			return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, projectCode+ " Project deallocated  to user "+ user.getFirstName() + " " + user.getLastName());
	}

	@Override
	public ResponseEntity<String> getAssignedProjects(String email) {
		User user= userRepository.findByEmail(email);
		if (user==null)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Email id is not registered");
		
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

}

