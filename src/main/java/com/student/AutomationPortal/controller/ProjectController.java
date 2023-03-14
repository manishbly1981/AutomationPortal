package com.student.AutomationPortal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.service.ProjectService;

@RestController
@RequestMapping("/api/v1/admin")
public class ProjectController {
	ProjectService projectService;
	public ProjectController(ProjectService projectService) {
		this.projectService= projectService;
	}

	@GetMapping("/projects")
	public ResponseEntity<List<Project>> getProjectList() {
		return ResponseEntity.ok(projectService.getAllProjects());
	}
}
