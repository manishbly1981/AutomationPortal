package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionTC;
import com.student.AutomationPortal.model.TestCase;

import java.util.List;

public interface ExecutionTCService {
    public List<ExecutionTC> addTestCasesToCycle(Long cycleId, List<TestCase> toAddTCsToCycle);
    public List<ExecutionTC> modifyTestCasesinCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle);
    public List<ExecutionTC> getTestCasesPerCycle(Long cycleId);
    public List<ExecutionTC> getTestCasesPerRelease(Long releaseId);
    public List<ExecutionTC> getAllTestCasesInExecution();
    public List<ExecutionTC> delTestCasesFromCycle(Long cycleId, List<ExecutionTC> ListOfTCsToDel);

}
