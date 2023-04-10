package com.student.AutomationPortal.repository;

import com.student.AutomationPortal.model.ExecutionTC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionTcRepository extends JpaRepository<ExecutionTC, Long> {
    List<ExecutionTC> findByCyclesId(Long cyclesId);
    List<ExecutionTC> findByCyclesIdOrderByTcSeq(Long cyclesId);
    List<ExecutionTC> findByCyclesExecutionReleaseId(Long releaseId);
    List<ExecutionTC> findByCyclesIdAndTcName(Long cyclesId, String tcName);
    ExecutionTC findByCyclesIdAndModuleNameAndTcName(Long cyclesId, String moduleName, String tcName);
}
