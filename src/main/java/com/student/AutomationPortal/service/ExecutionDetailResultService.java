package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionDetailResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExecutionDetailResultService {
    ResponseEntity<String> addExecutionResultDetails(List<ExecutionDetailResult> executionDetailResult);
    ResponseEntity<String> getExecutionResultDetails(Long executionSummaryId);
    ResponseEntity<String> delExecutionResultDetails(Long executionSummaryId);
}
