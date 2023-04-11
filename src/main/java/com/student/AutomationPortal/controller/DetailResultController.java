package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.ExecutionDetailResult;
import com.student.AutomationPortal.model.ExecutionSummaryResult;
import com.student.AutomationPortal.service.ExecutionDetailResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dr")
public class DetailResultController {
    @Autowired
    ExecutionDetailResultService executionDetailResultService;

    @PostMapping("")
    public ResponseEntity<String> addDetailsResult(@RequestBody List<ExecutionDetailResult> executionDetailResult){
        return executionDetailResultService.addExecutionResultDetails(executionDetailResult);
    }

    @GetMapping("")
    public ResponseEntity<String> getDetailsResult(@RequestParam(name="executionSummaryResultId", required = false) Long executionSummaryResultId){
        return executionDetailResultService.getExecutionResultDetails(executionSummaryResultId);
    }

    @DeleteMapping("")
    public ResponseEntity<String> delDetailsResult(@RequestParam(name="executionSummaryResultId", required = false) Long executionSummaryResultId){
        return executionDetailResultService.delExecutionResultDetails(executionSummaryResultId);
    }
}
