package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.TestCase;
import com.student.AutomationPortal.model.TestStep;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;

public interface TestStepService {
    ResponseEntity<String> modifyTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps);

    ResponseEntity<String> deleteTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps);
    ResponseEntity<String> getTestSteps(String email, String projectName, String moduleName, String testCaseName);
}
