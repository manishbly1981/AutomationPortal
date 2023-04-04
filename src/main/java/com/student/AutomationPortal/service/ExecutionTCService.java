package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionTC;
import com.student.AutomationPortal.model.TestCase;

import java.util.List;

public interface ExecutionTCService {
    public List<ExecutionTC> addTestCasesToCycle(Long cycleId, List<TestCase> toAddTCsToCycle);
    public List<ExecutionTC> modifyTestCasesinCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle);
    public List<ExecutionTC> getTestCasesPerCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle);
    public List<ExecutionTC> getTestCasesPerRelease(Long releaseId, List<TestCase> modifyListOfTCsToCycle);
    public List<ExecutionTC> getAllTestCasesInExecution(Long releaseId, List<TestCase> modifyListOfTCsToCycle);
    public List<ExecutionTC> delTestCasesFromCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle);
}
