package com.student.AutomationPortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.AutomationPortal.model.Locators ;

public interface LocatorRepository extends JpaRepository<Locators, Long>{
	List<Locators> findBySeq(String seq);
	List<Locators> findByPage(String page);
	List<Locators> findByLogicalName(String logicalName);
	List<Locators> findByLocatorType(String locatorType);
	List<Locators> findByPriority(String Priority);
	
}
