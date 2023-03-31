package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestStepRepository extends JpaRepository<TestStep, Long> {
    List<TestStep> findByOrderBySeqAsc();
    List<TestStep> findByTestCasesIdOrderBySeqAsc(Long id);

}
