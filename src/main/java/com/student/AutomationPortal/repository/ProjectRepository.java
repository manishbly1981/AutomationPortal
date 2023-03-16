package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	Project findByProjectCode(String projectCode);
	Project findByProjectName(String projectName);
	
}
