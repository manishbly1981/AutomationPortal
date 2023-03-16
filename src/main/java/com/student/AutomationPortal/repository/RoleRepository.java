package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.Role;

public interface RoleRepository  extends JpaRepository<Role, Long>{
	Role findById(String id);
	Role findByRole(String role);
}
