package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.User;

public interface ProjectService {
	ResponseEntity<String> registerProject(Project project);
	ResponseEntity<String> allocateProject(String email, String projectCode);
	ResponseEntity<String> deAllocateProject(String email, String projectCode);
	ResponseEntity<String> getAssignedProjects(String email);
	
	
	
	
}
