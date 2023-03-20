package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

import com.student.AutomationPortal.model.Project;

public interface ProjectService {
	ResponseEntity<String> registerProject(Project project);
	ResponseEntity<String> allocateProject(String email, String projectCode);
	ResponseEntity<String> deAllocateProject(String email, String projectCode);
	ResponseEntity<String> getAssignedProjects(String email);
	ResponseEntity<String> getAllProjects();
	ResponseEntity<String> deleteProject(String email, String projectCode);
	
}
