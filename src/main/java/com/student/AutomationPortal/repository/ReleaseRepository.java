package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.ExecutionRelease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<ExecutionRelease, Long> {
    List<ExecutionRelease> findByName(String name);
    List<ExecutionRelease> findByProjectId(Long projectId);
    List<ExecutionRelease> findByNameAndProjectId(String name, Long projectId);
    //List<Release> findByProjectprojectCode(String projectprojectCode);
}
