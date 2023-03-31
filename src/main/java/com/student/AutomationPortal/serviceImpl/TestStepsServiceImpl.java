package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.*;
import com.student.AutomationPortal.repository.*;
import com.student.AutomationPortal.service.TestStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    ModuleRepository moduleRepository;
    @Override
    public ResponseEntity<String> modifyTestSteps(String email, String projectName, String moduleName, String testCaseName, List<TestStep> testSteps) {
        Module module= moduleServiceImpl.getModule(email, projectName, moduleName);
        if (module==null)
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the project and module details");
        List<TestCase> tcs= module.getTestCases().stream().filter(tc->tc.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        if(tcs.size()<=0) {
            TestCase tcToAdd= new TestCase();
            tcToAdd.setName(testCaseName);
//            testCaseRepository.save(tcToAdd);
            Set<TestCase> testCases = module.getTestCases();
            testCases.add(tcToAdd);
            module.setTestCases(testCases);
            moduleRepository.save(module);
//            tcs= testCaseRepository.findByName(testCaseName);
            tcs= moduleRepository.findByName(moduleName).getTestCases().stream().filter(t->t.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
        }
        TestCase tc= tcs.get(0);

        objectupdater(tc, tc.getTestSteps(), testSteps);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, testStepRepository.findByTestCasesIdOrderBySeqAsc(tc.getId()));
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
            return CompactServiceImpl.reportResponse(HttpStatus.OK,testStepRepository.findByTestCasesIdOrderBySeqAsc(testCase.get().getId()));
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Check the test case name: " + testCaseName);
        }
    }

    private void objectupdater(TestCase tc, List<TestStep> existingTestSteps, List<TestStep> newTestSteps){
        List<TestStep> testStepsToAdd= new ArrayList<>();
        List<TestStep> testStepsToDel= new ArrayList<>();
        int currentSeq=0;
        if (existingTestSteps.size()>=newTestSteps.size()){
            for(int counter=0;counter<newTestSteps.size();counter++){
                final int temp=counter+1;
                TestStep ts= existingTestSteps.stream().filter(t->(t.getSeq()==temp)).findFirst().get();
                TestStep updatedTestStep= newTestSteps.get(counter);
                ts.setStepDescription(updatedTestStep.getStepDescription());
                ts.setAction(updatedTestStep.getAction());
                ts.setSeq(++currentSeq);
                //OR
                Set<Locators> updatedLocators= new HashSet<>();
                for(Locators lc:updatedTestStep.getLocators()) {
                    Locators lr = locatorRepository.findByPageAndLogicalNameAndSeq(lc.getPage(), lc.getLogicalName(),1);
                    if(lr==null){
                        throw new RuntimeException("Cannot find any object with page "+ lc.getPage() + " and name " + lc.getLogicalName() + " with seq 1");
                    }else
                        updatedLocators.add(lr);
                }

                ts.setLocators(updatedLocators);
                ts.setValue(updatedTestStep.getValue());
                ts.setExitIfFail(updatedTestStep.getExitIfFail());
                testStepsToAdd.add(ts);
            }
            for(int counter=newTestSteps.size();counter<existingTestSteps.size();counter++){
                final int temp=counter+1;
                TestStep ts= existingTestSteps.stream().filter(t->(t.getSeq()==temp)).findFirst().get();
                testStepsToDel.add(ts);
            }
        }else{//if newTestSteps is greater than existing test steps
            for(int counter=0;counter<existingTestSteps.size();counter++){
                final int temp=counter+1;
                TestStep ts= existingTestSteps.stream().filter(t->(t.getSeq()==temp)).findFirst().get();
                TestStep updatedTestStep= newTestSteps.get(counter);
                ts.setStepDescription(updatedTestStep.getStepDescription());
                ts.setAction(updatedTestStep.getAction());
                ts.setSeq(++currentSeq);
                //OR
                Set<Locators> updatedLocators= new HashSet<>();
                for(Locators lc:updatedTestStep.getLocators()) {
                    Locators lr = locatorRepository.findByPageAndLogicalNameAndSeq(lc.getPage(), lc.getLogicalName(),1);
                    if(lr==null){
                        throw new RuntimeException("Cannot find any object with page "+ lc.getPage() + " and name " + lc.getLogicalName() + " with seq 1");
                    }else
                        updatedLocators.add(lr);
                }
                ts.setLocators(updatedLocators);
                ts.setValue(updatedTestStep.getValue());
                ts.setExitIfFail(updatedTestStep.getExitIfFail());
                testStepsToAdd.add(ts);
            }
            for(int counter=existingTestSteps.size();counter<newTestSteps.size();counter++){
                TestStep ts= newTestSteps.get(counter);
                ts.setTestCases(tc); //Set Test case
                ts.setSeq(++currentSeq);
                //Set OR
                Set<Locators> updatedLocators= new HashSet<>();
                for(Locators lc:ts.getLocators()) {
                    Locators lr = locatorRepository.findByPageAndLogicalNameAndSeq(lc.getPage(), lc.getLogicalName(),1);
                    if(lr==null){
                        throw new RuntimeException("Cannot find any object with page "+ lc.getPage() + " and name " + lc.getLogicalName() + " with seq 1");
                    }else
                        updatedLocators.add(lr);
                }
                ts.setLocators(updatedLocators);
                testStepsToAdd.add(ts);
            }
        }
        testStepRepository.saveAll(testStepsToAdd);
        testStepRepository.deleteAll(testStepsToDel);
    }
}
