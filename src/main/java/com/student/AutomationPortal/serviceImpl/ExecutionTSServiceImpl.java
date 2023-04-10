package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.*;
import com.student.AutomationPortal.repository.ExecutionTcRepository;
import com.student.AutomationPortal.repository.ExecutionTsRepsitory;
import com.student.AutomationPortal.repository.TestCaseRepository;
import com.student.AutomationPortal.repository.TestStepRepository;
import com.student.AutomationPortal.service.ExecutionTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExecutionTSServiceImpl implements ExecutionTSService {

    @Autowired
    ExecutionTcRepository executionTcRepository;
    @Autowired
    TestCaseRepository testCaseRepository;

    @Autowired
    TestStepRepository testStepRepository;
    @Autowired
    ExecutionTsRepsitory executionTsRepsitory;
    @Override
    public List<ExecutionTS> addTestStepsToBeExecuted(Long executionTCId, Long tcId) {
        Optional<ExecutionTC> executionTC = executionTcRepository.findById(executionTCId);
        Optional<TestCase> tc = testCaseRepository.findById(tcId);
        List<ExecutionTS> executionTSList= new ArrayList<>();
        if(executionTC.isPresent() && tc.isPresent()){
            testStepRepository.findByTestCasesIdOrderBySeqAsc(tcId).forEach(ts->{
                ExecutionTS executionTS= new ExecutionTS();
                executionTS.setAction(ts.getAction());
                executionTS.setExpected(ts.getExpected());
                executionTS.setObjective(ts.getObjective());
                executionTS.setStepSeq(ts.getSeq());
                executionTS.setValue(ts.getValue());

                Locators locators= ts.getLocators().stream().findFirst().get();
                executionTS.setLocatorLogicalName(locators.getLogicalName());
                executionTS.setLocatorPage(locators.getPage());
                executionTS.setLocatorType(locators.getLocatorType());
                executionTS.setLocatorValue(locators.getLocatorValue());

                executionTS.setExitOnFail(ts.getExitIfFail());
                executionTS.setExecutionTCs(executionTS.getExecutionTCs());
                executionTSList.add(executionTS);
            });
            executionTsRepsitory.saveAll(executionTSList);
        }else{
            new RuntimeException("Execution Test case/Plan test case is not present");
        }

        return executionTSList;
    }

    @Override
    public List<ExecutionTS> editTestStepsToBeExecuted(Long executionTCId, Long tcId) {
        Optional<ExecutionTC> executionTC = executionTcRepository.findById(executionTCId);
        Optional<TestCase> tc = testCaseRepository.findById(tcId);
        List<ExecutionTS> executionTSList = executionTC.get().getExecutionTS();
        List<TestStep> tsList = tc.get().getTestSteps();

        /***************************************/
        if(tsList==null){
            if(executionTSList!=null)
                executionTsRepsitory.deleteAll(executionTSList);
        } else if (executionTSList==null) {
            List<ExecutionTS> executionTSListToAdd = new ArrayList<>();
            if (executionTC.isPresent() && tc.isPresent()) {
                testStepRepository.findByTestCasesIdOrderBySeqAsc(tcId).forEach(ts -> {
                    ExecutionTS executionTS = new ExecutionTS();
                    executionTS.setAction(ts.getAction());
                    executionTS.setExpected(ts.getExpected());
                    executionTS.setObjective(ts.getObjective());
                    executionTS.setStepSeq(ts.getSeq());
                    executionTS.setValue(ts.getValue());

                    Locators locators = ts.getLocators().stream().findFirst().get();
                    executionTS.setLocatorLogicalName(locators.getLogicalName());
                    executionTS.setLocatorPage(locators.getPage());
                    executionTS.setLocatorType(locators.getLocatorType());
                    executionTS.setLocatorValue(locators.getLocatorValue());

                    executionTS.setExitOnFail(ts.getExitIfFail());
                    executionTS.setExecutionTCs(executionTS.getExecutionTCs());
                    executionTSListToAdd.add(executionTS);
                });
                executionTsRepsitory.saveAll(executionTSListToAdd);
            }
        }else if(executionTSList.size()<= tsList.size()){
            int counter=0;
            List<ExecutionTS> executionTSListToAdd = new ArrayList<>();
            for(counter=0;counter<executionTSList.size();counter++){
                TestStep ts= tsList.get(counter);
                ExecutionTS executionTS = executionTSList.get(counter);
                executionTS.setAction(ts.getAction());
                executionTS.setExpected(ts.getExpected());
                executionTS.setObjective(ts.getObjective());
                executionTS.setStepSeq(ts.getSeq());
                executionTS.setValue(ts.getValue());

                Locators locators = ts.getLocators().stream().findFirst().get();
                executionTS.setLocatorLogicalName(locators.getLogicalName());
                executionTS.setLocatorPage(locators.getPage());
                executionTS.setLocatorType(locators.getLocatorType());
                executionTS.setLocatorValue(locators.getLocatorValue());

                executionTS.setExitOnFail(ts.getExitIfFail());
                executionTS.setExecutionTCs(executionTS.getExecutionTCs());
                executionTSListToAdd.add(executionTS);
            }
            for(;counter< tsList.size();counter++){
                TestStep ts= tsList.get(counter);
                ExecutionTS executionTS = new ExecutionTS();
                executionTS.setAction(ts.getAction());
                executionTS.setExpected(ts.getExpected());
                executionTS.setObjective(ts.getObjective());
                executionTS.setStepSeq(ts.getSeq());
                executionTS.setValue(ts.getValue());

                Locators locators = ts.getLocators().stream().findFirst().get();
                executionTS.setLocatorLogicalName(locators.getLogicalName());
                executionTS.setLocatorPage(locators.getPage());
                executionTS.setLocatorType(locators.getLocatorType());
                executionTS.setLocatorValue(locators.getLocatorValue());

                executionTS.setExitOnFail(ts.getExitIfFail());
                executionTS.setExecutionTCs(executionTS.getExecutionTCs());
                executionTSListToAdd.add(executionTS);
            }
            executionTsRepsitory.saveAll(executionTSListToAdd);
        }else if(executionTSList.size()> tsList.size()){
            int counter=0;
            List<ExecutionTS> executionTSListToAdd = new ArrayList<>();
            List<ExecutionTS> executionTSListToDel = new ArrayList<>();
            for(counter=0;counter<tsList.size();counter++){
                TestStep ts= tsList.get(counter);
                ExecutionTS executionTS = executionTSList.get(counter);
                executionTS.setAction(ts.getAction());
                executionTS.setExpected(ts.getExpected());
                executionTS.setObjective(ts.getObjective());
                executionTS.setStepSeq(ts.getSeq());
                executionTS.setValue(ts.getValue());

                Locators locators = ts.getLocators().stream().findFirst().get();
                executionTS.setLocatorLogicalName(locators.getLogicalName());
                executionTS.setLocatorPage(locators.getPage());
                executionTS.setLocatorType(locators.getLocatorType());
                executionTS.setLocatorValue(locators.getLocatorValue());

                executionTS.setExitOnFail(ts.getExitIfFail());
                executionTS.setExecutionTCs(executionTS.getExecutionTCs());
                executionTSListToAdd.add(executionTS);
            }

            for(;counter<executionTSList.size();counter++){
                executionTSListToDel.add(executionTSList.get(counter));
            }
            executionTsRepsitory.saveAll(executionTSListToAdd);
            executionTsRepsitory.deleteAll(executionTSListToDel);
        }

        /***************************************/
        return executionTC.get().getExecutionTS();
    }

    @Override
    public List<ExecutionTS> getTestStepList(Long executionTCId) {
        Optional<ExecutionTC> executionTC = executionTcRepository.findById(executionTCId);

        if(executionTC.isPresent()){
            return executionTC.get().getExecutionTS();
        }else{
            return null;
        }

    }

    @Override
    public ResponseEntity<String> delTestSteps(Long executionTCId) {
        Optional<ExecutionTC> executionTC = executionTcRepository.findById(executionTCId);
        if(executionTC.isPresent()){
            executionTsRepsitory.deleteAll(executionTC.get().getExecutionTS());
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Execution Test Steps deleted");
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "Execution Test Steps not deleted");
        }
    }
}
