package com.student.AutomationPortal.service;

import com.student.AutomationPortal.model.ExecutionSummaryResult;

import java.util.List;

public interface ExecutionSummaryResultService {
    public List<ExecutionSummaryResult> addExecutionResultSummary(Long executionTCId);
    public List<ExecutionSummaryResult> getExecutionResultSummary(Long executionSummaryResultId);
    public List<ExecutionSummaryResult> modifyExecutionResultSummary(Long executionSummaryResultId);
    public List<ExecutionSummaryResult> delExecutionResultSummary(Long executionSummaryResultId);
}
