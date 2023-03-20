package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long>{

}
