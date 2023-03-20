package com.student.AutomationPortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.AutomationPortal.service.TestCaseService;
import com.student.AutomationPortal.serviceImpl.CompactServiceImpl;

@RestController
@RequestMapping("/api/v1/tc")
public class TestCaseController {
	@Autowired
	TestCaseService testCaseService;
	
	@PostMapping("")
	public ResponseEntity<String> addTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="testName") String testName) {
		if (projectCode!=null)
			return testCaseService.addTestCase(email, projectCode, moduleName, testName);
		else if(projectName!=null)
			return testCaseService.addTestCase(email, projectName, moduleName, testName);
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");	
		
	}
	
	@GetMapping("")
	public ResponseEntity<String> getTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName", required = false) String moduleName, @RequestParam(name="testName", required = false) String testName) {
		String project="";
		if(projectCode!=null)
			project=projectCode;
		else if(projectName!=null)
			project=projectName;
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
		if (moduleName==null)
			return testCaseService.getTestCase(email, project);
		if (testName==null)
			return testCaseService.getTestCase(email, project, moduleName);
		else
			return testCaseService.getTestCase(email, project, moduleName, testName);
	}
	
	@PutMapping("")
	public ResponseEntity<String> reNameTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="currentTestName") String testName, @RequestParam(name="NewTestName") String newTestName) {
		String project="";
		if(projectCode!=null)
			project=projectCode;
		else if(projectName!=null)
			project=projectName;
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
		return testCaseService.reNameTestCase(email, project, moduleName, testName, newTestName);
	}
	
	@DeleteMapping("")
	public ResponseEntity<String> deleteTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="testName", required = false) String testName) {
		String project="";
		if(projectCode!=null)
			project=projectCode;
		else if(projectName!=null)
			project=projectName;
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
		return testCaseService.deleteTestCase(email, project, moduleName, testName);
	}
}
