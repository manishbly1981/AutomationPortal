package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.ExecutionTC;
import com.student.AutomationPortal.model.TestCase;
import com.student.AutomationPortal.repository.CycleRepository;
import com.student.AutomationPortal.service.ExecutionTCService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ExecutionTCServiceImpl implements ExecutionTCService {

    @Autowired
    CycleRepository cycleRepository;

    @Override
    public List<ExecutionTC> addTestCasesToCycle(Long cycleId, List<TestCase> toAddTCsToCycle) {
        List<ExecutionTC> executionTCList= new ArrayList<>();
        toAddTCsToCycle.forEach(tc->
        {
            ExecutionTC etc= new ExecutionTC();
            etc.setCycle(cycleRepository.findById(cycleId).get());
            //etc.setExecutionSummary();
            etc.setTcName(tc.getName());
//            etc.setModuleName();
            //etc.setTcSeq();
        });
        return null;
    }

    @Override
    public List<ExecutionTC> modifyTestCasesinCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle) {
        return null;
    }

    @Override
    public List<ExecutionTC> getTestCasesPerCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle) {
        return null;
    }

    @Override
    public List<ExecutionTC> getTestCasesPerRelease(Long releaseId, List<TestCase> modifyListOfTCsToCycle) {
        return null;
    }

    @Override
    public List<ExecutionTC> getAllTestCasesInExecution(Long releaseId, List<TestCase> modifyListOfTCsToCycle) {
        return null;
    }

    @Override
    public List<ExecutionTC> delTestCasesFromCycle(Long cycleId, List<TestCase> modifyListOfTCsToCycle) {
        return null;
    }
}
