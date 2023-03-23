package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.*;
import com.student.AutomationPortal.repository.*;
import com.student.AutomationPortal.service.TestStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestStepsServiceImpl implements TestStepService {

    @Autowired
    ModuleServiceImpl moduleServiceImpl;
    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    LocatorRepository locatorRepository;
    @Override
    public ResponseEntity<String> addTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
        List<TestCase> tcs = module.getTestCases().stream().filter(tc -> tc.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        TestCase tc;
        if (tcs.size()>0) {
            tc = tcs.get(0);
        }else {
            tc = new TestCase();
            tc.setName(testCaseName);
        }

//        List<TestStep> currentTestSteps = tc.getTestSteps();
//        currentTestSteps.addAll(testSteps);
        tc.setTestSteps(testSteps);
        testCaseRepository.save(tc);

        return CompactServiceImpl.reportResponse(HttpStatus.OK, tc);
    }

    @Override
    public ResponseEntity<String> editTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");

        List<TestCase> tcs = module.getTestCases().stream().filter(tc -> tc.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        if (tcs.size()<=0)
            return  CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, testCaseName + " does not exist in the module " + moduleName);
        TestCase tc= tcs.get(0);
        List<TestStep> tsToDel= tc.getTestSteps();
        tc.setTestSteps(testSteps);
        testCaseRepository.save(tc);
        testStepRepository.deleteAll(tsToDel);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, tc);
    }

    @Override
    public ResponseEntity<String> deleteTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
        List<TestCase> tcs= module.getTestCases().stream().filter(tc->tc.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        if(tcs.size()<=0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, testCaseName + " does not exist in the module " + moduleName);
        TestCase tc= tcs.get(0);
        List<TestStep> currentTS = tc.getTestSteps();
        currentTS.removeAll(testSteps);
        tc.setTestSteps(currentTS);
        testCaseRepository.save(tc);
        testStepRepository.deleteAll(testSteps);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, tc);
    }

    @Override
    public ResponseEntity<String> getTestSteps(String email, String projectName, String moduleName, String testCaseName) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
        Optional<TestCase> testCase = module.getTestCases().stream().filter(tc -> tc.getName().equalsIgnoreCase(testCaseName)).findFirst();
        if(testCase.isPresent()||testCase==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the test case name: "+ testCaseName);
        return CompactServiceImpl.reportResponse(HttpStatus.OK,testCase.get().getTestSteps());
    }
}
