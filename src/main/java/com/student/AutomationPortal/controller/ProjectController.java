package com.student.AutomationPortal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.service.ProjectService;

@RestController
@RequestMapping("/api/admin")
public class ProjectController {
	ProjectService projectService;
	public ProjectController(ProjectService projectService) {
		this.projectService= projectService;
	}

//	@PostMapping("/projects")
	@RequestMapping(value ="/projects", method= RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody Project project){
		return projectService.registerProject(project);
	}
//	@GetMapping("/projects")
@RequestMapping(value ="/projects", method= RequestMethod.GET)
	public ResponseEntity<String> getProjects() {
		return projectService.getAllProjects();
	}
	
//	@PostMapping("/assignProject")
	@RequestMapping(value ="/assignProject", method= RequestMethod.POST)
	public ResponseEntity<String> allocateProject(@RequestParam(name="email", required = true) String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if(projectCode!=null)
			return projectService.allocateProject(email, projectCode);
		else
			return projectService.allocateProject(email, projectName);
	}

	@RequestMapping(value ="/assignProject", method= RequestMethod.DELETE)
	public ResponseEntity<String> deallocateProject(@RequestParam(name="email", required = true) String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if(projectCode!=null)
			return projectService.deAllocateProject(email, projectCode);
		else
			return projectService.deAllocateProject(email, projectName);
	}

	@RequestMapping(value ="/assignProject", method= RequestMethod.GET)
	public ResponseEntity<String> getAssignProjects(@RequestParam(name="email", required = true) String email) {
		return projectService.getAssignedProjects(email);
	}
}
