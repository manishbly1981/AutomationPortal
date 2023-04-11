package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionSummaryResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExecutionSummaryResultService {
    public ResponseEntity<String> addExecutionResultSummary(ExecutionSummaryResult executionSummaryResult);
    public ResponseEntity<String> getExecutionResultSummary(Long executionSummaryResultId);
    public ResponseEntity<String> modifyExecutionResultSummary(Long executionSummaryResultId, ExecutionSummaryResult executionSummaryResult);
    public ResponseEntity<String> delExecutionResultSummary(Long executionSummaryResultId);
}
