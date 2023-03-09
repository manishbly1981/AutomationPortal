package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>{
	Module findByName(String name);
}
