package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.ExecutionSummaryResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionSummaryRepository extends JpaRepository<ExecutionSummaryResult, Long> {
}
