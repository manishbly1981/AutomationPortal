package com.student.AutomationPortal.controller;

import com.student.AutomationPortal.model.ExecutionTC;
import com.student.AutomationPortal.model.TestCase;
import com.student.AutomationPortal.service.ExecutionTCService;
import com.student.AutomationPortal.serviceImpl.CompactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/execution")
public class ExecutionController {
    @Autowired
    ExecutionTCService executionTCService;

    @PostMapping("")
    public ResponseEntity<String> addTCToExecutionCycle(@RequestParam(name = "cycleId") Long cycleId, @RequestBody List<TestCase> tcToAdd) {
        return CompactServiceImpl.reportResponse(HttpStatus.OK, executionTCService.addTestCasesToCycle(cycleId, tcToAdd));
    }

    @PutMapping("")
    public ResponseEntity<String> modifyTCToExecutionCycle(@RequestParam(name = "cycleId") Long cycleId, @RequestBody List<TestCase> tcToAdd) {
        return CompactServiceImpl.reportResponse(HttpStatus.OK, executionTCService.modifyTestCasesinCycle(cycleId, tcToAdd));
    }

    @GetMapping("")
    public ResponseEntity<String> getCycleTCs(@RequestParam(name = "cycleId") Long cycleId) {
        return CompactServiceImpl.reportResponse(HttpStatus.OK, executionTCService.getTestCasesPerCycle(cycleId));
    }
    @DeleteMapping("")
    public ResponseEntity<String> delCycleTCs(@RequestParam(name = "cycleId") Long cycleId, @RequestBody List<ExecutionTC> tcs) {
        return CompactServiceImpl.reportResponse(HttpStatus.OK, executionTCService.delTestCasesFromCycle(cycleId,tcs));
    }
}
