package com.student.AutomationPortal.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.Module;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.repository.ModuleRepository;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService{
	@Autowired 
	ModuleRepository moduleRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Override
	public ResponseEntity<String> RegisterModule(String projectCode, String moduleName) {
		Project projectList= projectRepository.findByProjectCode(projectCode);
		
		return null;
	}

	@Override
	public ResponseEntity<String> getModuleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> getModuleList(String ProjectCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
