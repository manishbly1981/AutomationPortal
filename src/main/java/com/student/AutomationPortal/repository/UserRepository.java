package com.student.AutomationPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.AutomationPortal.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	
//	@Query("Select * from Project where id in (Select project_id from user_project where user_id in (Select id from User where email= '?1'))")
//	List<Project> findAssignedProjects(String email);
}
