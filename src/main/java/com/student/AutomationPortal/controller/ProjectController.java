package com.student.AutomationPortal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.service.ProjectService;

@RestController
@RequestMapping("/api/v1/admin")
public class ProjectController {
	ProjectService projectService;
	public ProjectController(ProjectService projectService) {
		this.projectService= projectService;
	}

	@PostMapping("/projects")
	public ResponseEntity<String> registerUser(@RequestBody Project project){
		return projectService.registerProject(project);
	}
	@GetMapping("/projects")
	public ResponseEntity<String> getProjects() {
		return projectService.getAllProjects();
	}
	
	@PostMapping("/assignProject")
	public ResponseEntity<String> allocateProject(@RequestParam(name="email", required = true) String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if(projectCode!=null)
			return projectService.allocateProject(email, projectCode);
		else
			return projectService.allocateProject(email, projectName);
	}
	
	@DeleteMapping("/assignProject")
	public ResponseEntity<String> deallocateProject(@RequestParam(name="email", required = true) String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if(projectCode!=null)
			return projectService.deAllocateProject(email, projectCode);
		else
			return projectService.deAllocateProject(email, projectName);
	}
	
	@GetMapping("/assignProject")
	public ResponseEntity<String> getAssignProjects(@RequestParam(name="email", required = true) String email) {
		return projectService.getAssignedProjects(email);
	}
}
