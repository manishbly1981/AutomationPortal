package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.TestCase;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long>{
    List<TestCase> findByName(String name);
}
