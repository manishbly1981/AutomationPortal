package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.TestStep;

import java.util.List;

public interface ExecutionTSService {
    /*
    executionTCId= test cases of execution cycle where needs to add
    tcId= plan test case id from where steps needs to be copied
     */
    public List<TestStep> addTestStepsToBeExecuted(Long executionTCId, Long tcId);
    public List<TestStep> editTestStepsToBeExecuted(Long executionTCId, Long tcId);

    public List<TestStep> getTestStepList(Long executionTCId);

    public List<TestStep> delTestSteps(Long executionTCId);

}
