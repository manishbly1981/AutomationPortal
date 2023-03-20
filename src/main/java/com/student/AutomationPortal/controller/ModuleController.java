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

import com.student.AutomationPortal.service.ModuleService;
import com.student.AutomationPortal.serviceImpl.CompactServiceImpl;

@RestController
@RequestMapping("/api/v1/module")
public class ModuleController {

	@Autowired
	ModuleService moduleService;
	
	@PostMapping("")
	public ResponseEntity<String> registerModule(@RequestParam(name="email") String email, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if (projectCode!=null)
			return moduleService.registerModule(email, projectCode, moduleName);
		else if(projectName!=null)
			return moduleService.registerModule(email, projectName, moduleName);
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
	}
	 
	@GetMapping("")
	public ResponseEntity<String> getModules(@RequestParam(name="email") String email, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName){
		if (projectCode!=null)
			return moduleService.getModuleList(email, projectCode);
		else if(projectName!=null)
			return moduleService.getModuleList(email, projectName);
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
	}
	
	@PutMapping("")
	public ResponseEntity<String> deRegisterModule(@RequestParam(name="email") String email, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if (projectCode!=null)
			return moduleService.deRegisterModule(email, projectCode, moduleName);
		else if(projectName!=null)
			return moduleService.deRegisterModule(email, projectName, moduleName);
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
	}
	
	@DeleteMapping("")
	public ResponseEntity<String> deleteModule(@RequestParam(name="email") String email, @RequestParam(name="moduleName") String moduleName, @RequestParam(name="projectCode", required = false) String projectCode, @RequestParam(name="projectName", required = false) String projectName) {
		if (projectCode!=null)
			return moduleService.deleteModule(email, projectCode, moduleName);
		else if(projectName!=null)
			return moduleService.deleteModule(email, projectName, moduleName);
		else
			return CompactServiceImpl.reportResponse(HttpStatus.BAD_REQUEST, "Project code or Project Name is mandatory");
	}
}
