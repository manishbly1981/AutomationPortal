package com.student.AutomationPortal.serviceImpl;

import com.student.AutomationPortal.model.ExecutionDetailResult;
import com.student.AutomationPortal.repository.ExecutionDetailsResultRepository;
import com.student.AutomationPortal.repository.ExecutionSummaryRepository;
import com.student.AutomationPortal.service.ExecutionDetailResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecutionDetailResultServiceImpl implements ExecutionDetailResultService {

    @Autowired
    ExecutionDetailsResultRepository executionDetailsResultRepository;
    @Autowired
    ExecutionSummaryRepository executionSummaryRepository;
    @Override
    public ResponseEntity<String> addExecutionResultDetails(List<ExecutionDetailResult> executionDetailResult) {
        executionDetailResult.stream().forEach(ed->{
            ed.setExecutionSummaryResults(executionSummaryRepository.findById(ed.getExecutionSummaryResults().getId()).get());
        });
        executionDetailsResultRepository.saveAll(executionDetailResult);
        return CompactServiceImpl.reportResponse(HttpStatus.OK, "Detail Result Added");
    }

    @Override
    public ResponseEntity<String> getExecutionResultDetails(Long executionSummaryId) {
        return CompactServiceImpl.reportResponse(HttpStatus.OK, executionDetailsResultRepository.findByExecutionSummaryResultsId(executionSummaryId));
    }

    @Override
    public ResponseEntity<String> delExecutionResultDetails(Long executionSummaryId) {
        executionDetailsResultRepository.deleteAll(executionDetailsResultRepository.findByExecutionSummaryResultsId(executionSummaryId));
        return CompactServiceImpl.reportResponse(HttpStatus.OK, "Details of result is deleted");
    }
}
