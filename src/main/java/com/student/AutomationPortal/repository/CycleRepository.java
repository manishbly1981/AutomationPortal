package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.Cycle;
import com.student.AutomationPortal.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CycleRepository extends JpaRepository<Cycle, Long> {
    List<Cycle> findByName(String name);
    List<Cycle> findByNameAndExecutionReleaseId(String name, Long executionReleasedId);
    Cycle findByExecutionReleaseNameAndName(String releaseName, String cycleName);


}
