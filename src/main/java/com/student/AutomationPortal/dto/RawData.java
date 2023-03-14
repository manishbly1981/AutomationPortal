package com.student.AutomationPortal.dto;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.student.AutomationPortal.model.LocatorObjects;
//import com.student.AutomationPortal.model.Module;
//import com.student.AutomationPortal.model.Project;
//import com.student.AutomationPortal.model.TestCase;
//import com.student.AutomationPortal.model.TestStep;
import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.repository.UserRepository;

@Service
public class RawData {
	UserRepository userRepository;
	public RawData(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	public void RawDataGenerator() {

		User user1= new User();
		user1.setEmail("manish.bly@gmail.com");
		user1.setActive(true);
		user1.setAttempts(0);
		user1.setFirstName("manish");
		user1.setLastName("gupta");
		user1.setPassword("Niit@123");
		this.userRepository.save(user1);

		User user2= new User();
		user2.setEmail("arti@aarti.com");
		user2.setActive(true);
		user2.setAttempts(0);
		user2.setFirstName("arti");
		user2.setLastName("jarariya");
		user2.setPassword("Niit@123");
		this.userRepository.save(user2);
		
		User user3= new User();
		user3.setEmail("vikas@vikas.com");
		user3.setActive(true);
		user3.setAttempts(0);
		user3.setFirstName("vikas");
		user3.setLastName("pandey");
		user3.setPassword("Niit@123");
		this.userRepository.save(user3);
//		Project project= new Project();
//		project.setProjectCode("SAN");
//		project.setProjectName("Santander");
//		
//		HashSet<Project> projects= new HashSet<>();
//		
//		projects.add(project);
//		
//		user.setProjects(projects);
//		user.setRoles(null);
		
//		Module module= new Module();
//		module.setName("Regression");
//		
//		TestCase tc= new TestCase();
//		tc.setName("Test Case 1");
//		
//		TestStep ts= new TestStep();
//		ts.setAction("Action 1");
//		
//		LocatorObjects lo= new LocatorObjects();
//		
//		lo.setLocatorName("Xpath");
//		lo.setLocatorValue("//a");
//		
//		HashSet<LocatorObjects> los= new HashSet<>();
//		los.add(lo);
//		ts.setLocatorId(los);
//		
//		ts.setLogicalName("logicalName1");
//		ts.setStepDescription("This is dummy step 1");
//		ts.setExitIfFail("Yes");
//		ts.setSNo(1);
//		ts.setValue("Data 1");
//		
//		HashSet<TestStep> tss= new HashSet<>();
//		tc.setTestSteps(tss);
//		
//		HashSet<TestCase> tcs= new HashSet<>();
//		
//		module.setTestCases(tcs);
//		HashSet<Module> modules= new HashSet<>();
//		modules.add(module);
//		project.setModules(modules);
		
	}

}
