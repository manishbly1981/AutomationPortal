package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.Configuration;
import com.student.AutomationPortal.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConfigRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findByName(String name);
    List<Configuration> findByProject(Project project);
}
