package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

public interface TestCaseService {
	ResponseEntity<String> addTestCase(String email, String projectCode, String moduleName, String testCaseName);
	ResponseEntity<String> reNameTestCase(String email, String projectCode, String moduleName, String oldTestCaseName, String newTestCaseName);
	ResponseEntity<String> getTestCase(String email, String projectCode);
	ResponseEntity<String> getTestCase(String email, String projectCode, String moduleName);
	ResponseEntity<String> getTestCase(String email, String projectCode, String moduleName, String testCaseName);
	ResponseEntity<String> deleteTestCase(String email, String projectCode, String moduleName, String testCaseName);

}
