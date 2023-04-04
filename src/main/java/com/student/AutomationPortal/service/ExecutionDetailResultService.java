package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionDetailResult;

import java.util.List;

public interface ExecutionDetailResultService {
    List<ExecutionDetailResult> addExecutionResultDetails(Long executionSummaryId);
    List<ExecutionDetailResult> modifyExecutionResultDetails(Long executionSummaryId);
    List<ExecutionDetailResult> getExecutionResultDetails(Long executionSummaryId);
    List<ExecutionDetailResult> delExecutionResultDetails(Long executionSummaryId);
}
