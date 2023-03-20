package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

public interface ModuleService {
	ResponseEntity<String> registerModule(String email, String projectCode, String moduleName);
	ResponseEntity<String> getModuleList();
	ResponseEntity<String> getModuleList(String email, String projectCode);
	ResponseEntity<String> deRegisterModule(String email, String projectCode, String moduleName);
	ResponseEntity<String> deleteModule(String email, String projectCode, String moduleName);
}
