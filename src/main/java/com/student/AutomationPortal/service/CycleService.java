package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

public interface CycleService {
    ResponseEntity<String> addCycle(String projectName, String releaseName, String cycleName);
    ResponseEntity<String>editCycle(String projectName, String releaseName, String cycleName, String newCycleName);
    ResponseEntity<String> getCycle(String projectName, String releaseName, String cycleName);
    ResponseEntity<String> delCycle(String projectName, String releaseName, String cycleName);
}
