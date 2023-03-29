package com.student.AutomationPortal.service;


import org.springframework.http.ResponseEntity;

public interface LocatorService {
	ResponseEntity<String> addLocator(String projectCode, String requestBody);
	ResponseEntity<String> editLocator(String projectCode, String requestBody);
	ResponseEntity<String> delLocator(String projectCode, String page, String locatorName);
	ResponseEntity<String> getLocator(String projectCode, String page, String locatorName);
	public ResponseEntity<String> getLocator(String projectCode, String page, String name, int seqNo);
}
