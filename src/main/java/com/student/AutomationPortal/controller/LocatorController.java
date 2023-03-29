package com.student.AutomationPortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.AutomationPortal.service.LocatorService;

@RestController
@RequestMapping("/api/v1/or")
public class LocatorController {

	@Autowired
	LocatorService locatorService;
	
	@PostMapping("")
	public ResponseEntity<String> addLocator(@RequestParam(name = "projectCode") String projectCode, @RequestBody String requestBody) {
		return locatorService.addLocator(projectCode, requestBody);
	}
	
	@PutMapping("")
	public ResponseEntity<String> editLocator(@RequestParam(name = "projectCode") String projectCode, @RequestBody String requestBody) {
		return locatorService.editLocator(projectCode, requestBody);
	}
	
	@GetMapping("")
	public ResponseEntity<String> getLocator(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name="page")  String page, @RequestParam(name="name") String name) {
		return locatorService.getLocator(projectCode, page, name);
	}
	
	@DeleteMapping("")
	public ResponseEntity<String> delLocator(@RequestParam(name = "projectCode") String projectCode, @RequestParam(name="page")  String page, @RequestParam(name="name") String name) {
		return locatorService.delLocator(projectCode, page, name);
	}
}
