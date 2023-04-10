package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.*;
import com.student.AutomationPortal.repository.CycleRepository;
import com.student.AutomationPortal.repository.ExecutionTcRepository;
import com.student.AutomationPortal.repository.TestCaseRepository;
import com.student.AutomationPortal.service.ExecutionTCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ExecutionTCServiceImpl implements ExecutionTCService {

    @Autowired
    CycleRepository cycleRepository;

    @Autowired
    TestCaseRepository testCaseRepository;
    @Autowired
    ExecutionTcRepository executionTcRepository;
    @Override
    public List<ExecutionTC> addTestCasesToCycle(Long cycleId, List<TestCase> toAddTCsToCycle) {
        int existingSize= executionTcRepository.findByCyclesId(cycleId).size();
        List<ExecutionTC> executionTCList= new ArrayList<>();
        for(int counter=0;counter<toAddTCsToCycle.size();counter++)
        {
            TestCase tc= testCaseRepository.findById(toAddTCsToCycle.get(counter).getId()).get();
            ExecutionTC etc= new ExecutionTC();
            etc.setModuleName(tc.getModules().getName());
            etc.setTcName(tc.getName());
            etc.setTcSeq(existingSize+counter+1);
            etc.setCycles(cycleRepository.findById(cycleId).get());
            executionTcRepository.save(etc);
            etc= executionTcRepository.findByCyclesIdAndModuleNameAndTcName(cycleId, etc.getModuleName(), etc.getTcName());
            List<ExecutionTS> executionTSList= new ArrayList<>();
            for(TestStep ts:tc.getTestSteps()){
                ExecutionTS executionTS= new ExecutionTS();
                executionTS.setExecutionTCs(etc);
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
            }
            etc.setExecutionTS(executionTSList);
            executionTCList.add(etc);
        };
        executionTcRepository.saveAll(executionTCList);
        return executionTcRepository.findByCyclesId(cycleId);
    }

    public List<ExecutionTC> addTestCasesToCycle(Long cycleId, TestCase toAddTCsToCycle) {
        int totTCS=0;
        try {
            totTCS = executionTcRepository.findByCyclesId(cycleId).size();
        }catch(NullPointerException e){
            totTCS=0;
        }
        ExecutionTC etc= new ExecutionTC();
        etc.setModuleName(toAddTCsToCycle.getModules().getName());
        etc.setTcName(toAddTCsToCycle.getName());
        etc.setTcSeq(totTCS+1);
        etc.setCycles(cycleRepository.findById(cycleId).get());
        List<ExecutionTS> executionTSList= new ArrayList<>();
        for(TestStep ts:toAddTCsToCycle.getTestSteps()){
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
        }
        etc.setExecutionTS(executionTSList);
        executionTcRepository.save(etc);
        return executionTcRepository.findByCyclesId(cycleId);
    }

    @Override
    public List<ExecutionTC> modifyTestCasesinCycle(Long cycleId, List<TestCase> newTcList) {
        List<ExecutionTC> modifiedExecutionTCList= new ArrayList<>();
        for(int counter=0;counter<newTcList.size();counter++){
            TestCase ctc= newTcList.get(counter);
            ExecutionTC etc= executionTcRepository.findByCyclesIdAndModuleNameAndTcName(cycleId, ctc.getModules().getName(), ctc.getName());
            if(etc==null){
                etc= new ExecutionTC();
                etc.setModuleName(ctc.getModules().getName());
                etc.setTcName(ctc.getName());
                etc.setTcSeq(counter+1);
                etc.setCycles(cycleRepository.findById(cycleId).get());
                List<ExecutionTS> executionTSList= new ArrayList<>();
                for(TestStep ts:ctc.getTestSteps()) {
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
                    executionTSList.add(executionTS);
                }
                etc.setExecutionTS(executionTSList);
            }else{
                etc.setTcSeq(counter+1);
            }
            modifiedExecutionTCList.add(etc);
        }
        executionTcRepository.saveAll(modifiedExecutionTCList);
        return executionTcRepository.findByCyclesId(cycleId);
    }

    @Override
    public List<ExecutionTC> getTestCasesPerCycle(Long cycleId) {
        return executionTcRepository.findByCyclesId(cycleId);
    }

    @Override
    public List<ExecutionTC> getTestCasesPerRelease(Long releaseId) {
        return executionTcRepository.findByCyclesExecutionReleaseId(releaseId);
    }

    @Override
    public List<ExecutionTC> getAllTestCasesInExecution() {
        return executionTcRepository.findAll();
    }

    @Override
    public List<ExecutionTC> delTestCasesFromCycle(Long cycleId, List<ExecutionTC> ListOfTCsToDel) {
        for(int counter=0; counter<ListOfTCsToDel.size();counter++){
            ExecutionTC etc= executionTcRepository.findById(ListOfTCsToDel.get(counter).getId()).get();
            if (etc.getId()==null)//if id is not mentioned then cycleId, modulename and test casse name also could be used to del
                etc= executionTcRepository.findByCyclesIdAndModuleNameAndTcName(cycleId, etc.getModuleName(), etc.getTcName());

            if (etc!=null)
                ListOfTCsToDel.set(counter, etc);
        }
        executionTcRepository.deleteAll(ListOfTCsToDel);
        List<ExecutionTC> tcList = executionTcRepository.findByCyclesIdOrderByTcSeq(cycleId);
        for(int counter=0;counter<tcList.size();counter++){
            tcList.get(counter).setTcSeq(counter+1);
        }
        executionTcRepository.saveAll(tcList);
        return getTestCasesPerCycle(cycleId);
    }
}
