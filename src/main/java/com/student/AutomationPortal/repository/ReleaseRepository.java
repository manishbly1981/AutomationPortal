package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
    List<Release> findByName(String name);
    List<Release> findByProjectId(Long projectId);
    //List<Release> findByProjectprojectCode(String projectprojectCode);
}
