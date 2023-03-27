package com.student.AutomationPortal.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReleaseService {
    ResponseEntity<String> addRelease(String projectName, String releaseName);
    ResponseEntity<String> editRelease(String projectName, String releaseName, String newReleaseName);
    ResponseEntity<String> getRelease(String projectName, String releaseName);
    ResponseEntity<String> deleteRelease(String projectName, String releaseName);
}
