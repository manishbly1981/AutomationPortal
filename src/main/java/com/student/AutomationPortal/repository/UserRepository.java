package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.AutomationPortal.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

}
