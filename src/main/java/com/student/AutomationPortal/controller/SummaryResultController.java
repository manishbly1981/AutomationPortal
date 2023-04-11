package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.ExecutionSummaryResult;
import com.student.AutomationPortal.service.ExecutionSummaryResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sr")
public class SummaryResultController {
    @Autowired
    ExecutionSummaryResultService executionSummaryResultService;

    @PostMapping("")
    public ResponseEntity<String> addSummaryResult(@RequestBody ExecutionSummaryResult executionSummaryResult){
        return executionSummaryResultService.addExecutionResultSummary(executionSummaryResult);
    }

    @PutMapping("")
    public ResponseEntity<String> modifySummaryResult(@RequestParam(name="executionSummaryResultId", required = false) Long executionSummaryResultId, @RequestBody ExecutionSummaryResult executionSummaryResult){
        return executionSummaryResultService.modifyExecutionResultSummary(executionSummaryResultId, executionSummaryResult);
    }

    @GetMapping("")
    public ResponseEntity<String> getSummaryResult(@RequestParam(name="executionSummaryResultId", required = false) Long executionSummaryResultId){
        return executionSummaryResultService.getExecutionResultSummary(executionSummaryResultId);
    }

    @DeleteMapping("")
    public ResponseEntity<String> delSummaryResult(@RequestParam(name="executionSummaryResultId", required = false) Long executionSummaryResultId){
        return executionSummaryResultService.delExecutionResultSummary(executionSummaryResultId);
    }

}
