package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.Cycle;
import com.student.AutomationPortal.model.Release;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CycleService {
    ResponseEntity<String> addCycle(String projectName, String releaseName, String cycleName);
    ResponseEntity<String>editCycle(String projectName, String releaseName, String cycleName, String newCycleName);
    ResponseEntity<String> getCycle(String projectName, String releaseName, String cycleName);
    ResponseEntity<String> delCycle(String projectName, String releaseName, String cycleName);
}
