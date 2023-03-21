package com.student.AutomationPortal.service;


import org.springframework.http.ResponseEntity;

public interface LocatorService {
	ResponseEntity<String> addLocator(String requestBody);
	ResponseEntity<String> editLocator(String requestBody);
	ResponseEntity<String> delLocator(String page, String locatorName);
	ResponseEntity<String> getLocator(String page, String locatorName);
}
