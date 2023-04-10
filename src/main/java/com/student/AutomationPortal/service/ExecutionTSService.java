package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionTS;
import com.student.AutomationPortal.model.TestStep;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface ExecutionTSService {
    /*
    executionTCId= test cases of execution cycle where needs to add
    tcId= plan test case id from where steps needs to be copied
     */
    public List<ExecutionTS> addTestStepsToBeExecuted(Long executionTCId, Long tcId);
    public List<ExecutionTS> editTestStepsToBeExecuted(Long executionTCId, Long tcId);

    public List<ExecutionTS> getTestStepList(Long executionTCId);

    public ResponseEntity<String> delTestSteps(Long executionTCId);

}
