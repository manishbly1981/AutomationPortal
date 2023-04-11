package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.ExecutionDetailResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionDetailsResultRepository extends JpaRepository<ExecutionDetailResult, Long> {
    List<ExecutionDetailResult> findByExecutionSummaryResultsId(Long ExecutionSummaryResultsId);
}
