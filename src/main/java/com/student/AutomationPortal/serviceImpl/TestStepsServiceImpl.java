package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.*;
import com.student.AutomationPortal.repository.*;
import com.student.AutomationPortal.service.TestStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public ResponseEntity<String> modifyTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
        List<TestCase> tcs= module.getTestCases().stream().filter(tc->tc.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        if(tcs.size()<=0) {
            TestCase tcToAdd= new TestCase();
            tcToAdd.setName(testCaseName);
            testCaseRepository.save(tcToAdd);
            tcs= testCaseRepository.findByName(testCaseName);
        }
        TestCase tc= tcs.get(0);
        List<TestStep> currentTS = tc.getTestSteps();
        List<TestStep> stepsToRemove= currentTS.stream().filter(ele->!testSteps.contains(ele)).collect(Collectors.toList());
        List<TestStep> stepsToAdd= testSteps.stream().filter(ele->!currentTS.contains(ele)).collect(Collectors.toList());
        List<String> nonExistingElementList= new ArrayList<>();
        stepsToAdd.stream().forEach(step->{
            Set<Locators> currentStepLocators= step.getLocator();

            String logicalName= currentStepLocators.stream().findFirst().get().getLogicalName();
            List<Locators> existingLocatorFromDb = locatorRepository.findByLogicalName(logicalName);
            Set<Locators> firstEle= existingLocatorFromDb.stream().filter(loc->loc.getSeq()==1).collect(Collectors.toSet());
            if(existingLocatorFromDb.size()<=0)
                nonExistingElementList.add(logicalName);
            else{
                step.setLocator(firstEle);
            }
        });
        if (nonExistingElementList.size()>0)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND,String.join(",", nonExistingElementList) + " elements are not present in Object Repository");
        currentTS.removeAll(stepsToRemove);
        currentTS.addAll(stepsToAdd);
        tc.setTestSteps(currentTS);
        testCaseRepository.save(tc);
        testStepRepository.deleteAll(stepsToRemove);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, currentTS);
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
        if(testCase.isPresent()) {
            return CompactServiceImpl.reportResponse(HttpStatus.OK,testCase.get().getTestSteps());
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the test case name: " + testCaseName);
        }
    }
}
