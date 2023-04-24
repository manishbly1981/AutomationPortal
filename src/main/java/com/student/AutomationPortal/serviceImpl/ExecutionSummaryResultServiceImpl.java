package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.ExecutionSummaryResult;
import com.student.AutomationPortal.model.ExecutionTC;
import com.student.AutomationPortal.repository.ExecutionSummaryRepository;
import com.student.AutomationPortal.repository.ExecutionTcRepository;
import com.student.AutomationPortal.service.ExecutionSummaryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutionSummaryResultServiceImpl implements ExecutionSummaryResultService {
    @Autowired
    ExecutionSummaryRepository executionSummaryRepository;
    @Autowired
    ExecutionTcRepository executionTcRepository;
    @Override
    public ResponseEntity<String> addExecutionResultSummary(ExecutionSummaryResult executionSummaryResult) {
        Optional<ExecutionTC> executiontc = executionTcRepository.findById(executionSummaryResult.getExecutionTc().getId());
        if (executiontc.isPresent()) {
            executionSummaryResult.setExecutionTc(executiontc.get());
            executionSummaryRepository.save(executionSummaryResult);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Summary Result Added");
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Test Case not found");
        }
    }

    @Override
    public ResponseEntity<String> getExecutionResultSummary(Long executionTestCaseId) {
        List<ExecutionSummaryResult> executionSummaryResult = executionSummaryRepository.findByExecutionTcIdOrderByIdDesc(executionTestCaseId);
        if(executionSummaryResult.size()>0)
            return CompactServiceImpl.reportResponse(HttpStatus.OK, executionSummaryResult);
        else
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "No report found");
    }

    @Override
    public ResponseEntity<String> modifyExecutionResultSummary(Long executionSummaryResultId, ExecutionSummaryResult executionSummaryResult) {
        Optional<ExecutionTC> executiontc = executionTcRepository.findById(executionSummaryResult.getExecutionTc().getId());
        if (executiontc.isPresent()) {
            executionSummaryResult.setExecutionTc(executiontc.get());
            executionSummaryRepository.save(executionSummaryResult);
            return CompactServiceImpl.reportResponse(HttpStatus.OK, executionSummaryRepository.findById(executionSummaryResultId).get());
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Test case not found in execution set");
        }

    }

    @Override
    public ResponseEntity<String> delExecutionResultSummary(Long executionSummaryResultId) {
        Optional<ExecutionSummaryResult> executionSummary = executionSummaryRepository.findById(executionSummaryResultId);
        if (executionSummary.isPresent()) {
            executionSummaryRepository.delete(executionSummaryRepository.findById(executionSummaryResultId).get());
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Result Deleted");
        }else{
            return CompactServiceImpl.reportResponse(HttpStatus.OK, "Result not found");
        }
    }
}
