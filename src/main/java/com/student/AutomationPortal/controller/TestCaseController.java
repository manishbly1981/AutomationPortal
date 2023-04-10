package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.Module;
import com.student.AutomationPortal.model.TestStep;
import com.student.AutomationPortal.service.TestStepService;
import com.student.AutomationPortal.serviceImpl.ModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.AutomationPortal.service.TestCaseService;
import com.student.AutomationPortal.serviceImpl.CompactServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tc")
public class TestCaseController {
	@Autowired
	TestCaseService testCaseService;
	@Autowired
	TestStepService testStepService;
	@Autowired
	ModuleServiceImpl moduleServiceImpl;
	@PostMapping("")
	public ResponseEntity<String> addTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="testName") String testName, @RequestBody(required = false) List<TestStep> testSteps) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if(module==null) {
			module = moduleServiceImpl.getModule(email, projectName, moduleName);
			if (module == null)
				return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
		}
		if (testSteps==null) {
			return testCaseService.addTestCase(email, projectCode, moduleName, testName);
		}else {

			return testStepService.modifyTestSteps(email, projectCode, moduleName, testName, testSteps);

		}
	}
	
	@GetMapping("")
	public ResponseEntity<String> getTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName", required = false) String moduleName, @RequestParam(name="testName", required = false) String testName, @RequestParam(name="entity", required = true) String entity) {
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
		else{
			if(entity.equalsIgnoreCase("testcase"))
				return testCaseService.getTestCase(email, project, moduleName, testName);
			else
				return testStepService.getTestSteps(email, project, moduleName, testName);
		}

	}
	
	@PutMapping("")
	public ResponseEntity<String> updateTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="currentTestName") String testName, @RequestParam(name="newTestName", required = false) String newTestName, @RequestBody(required = false) List<TestStep> testSteps) {
		String project="";
		if(projectCode!=null)
			project=projectCode;
		else if(projectName!=null)
			project=projectName;
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
		if (newTestName!=null && testSteps==null)
			return testCaseService.reNameTestCase(email, project, moduleName, testName, newTestName);
		else if (newTestName!=null)
			testCaseService.reNameTestCase(email, project, moduleName, testName, newTestName);

		if (testSteps!=null){
			return testStepService.modifyTestSteps(email, project, moduleName, testName, testSteps);
		}else{
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Test Case Name or Test Steps are missing");
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<String> deleteTestCase(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="testName", required = false) String testName, @RequestBody(required = false) List<TestStep> testSteps) {
		String project="";
		if(projectCode!=null)
			project=projectCode;
		else if(projectName!=null)
			project=projectName;
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
		if (testSteps!=null)
			return testStepService.deleteTestSteps(email, project, moduleName, testName, testSteps);
		else
			return testCaseService.deleteTestCase(email, project, moduleName, testName);
	}
}
