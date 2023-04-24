package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.ExecutionSummaryResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionSummaryRepository extends JpaRepository<ExecutionSummaryResult, Long> {
        List<ExecutionSummaryResult> findByExecutionTcIdOrderByIdDesc(Long executionTcId);
}
