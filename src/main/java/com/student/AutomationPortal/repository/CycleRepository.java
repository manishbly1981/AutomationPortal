package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleRepository extends JpaRepository<Cycle, Long> {
    List<Cycle> findByName(String name);
}
