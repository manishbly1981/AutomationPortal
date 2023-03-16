package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

public interface ModuleService {
	ResponseEntity<String> RegisterModule(String projectCode, String moduleName);
	ResponseEntity<String> getModuleList();
	ResponseEntity<String> getModuleList(String ProjectCode);
}
