package com.student.AutomationPortal.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.Module;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.TestCase;
import com.student.AutomationPortal.repository.ModuleRepository;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.TestCaseRepository;
import com.student.AutomationPortal.service.TestCaseService;

@Service
public class TestCaseServiceImpl implements TestCaseService{

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	TestCaseRepository testCaseRepository;
	
	
	@Autowired
	ModuleServiceImpl moduleServiceImpl;
	
	@Autowired
	ProjectServiceImpl projectServiceImpl;
	
	@Override
 	public ResponseEntity<String> addTestCase(String email, String projectCode, String moduleName, String testCaseName) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if (module==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email/project/module details if are correct");
		
		Set<TestCase> testCases= module.getTestCases();
		List<TestCase> matchingTestCases= testCases.stream().filter(t->t.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
		if (matchingTestCases.size()>0)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, testCaseName + " test case already exist");

		
		TestCase tc= new TestCase();
		tc.setName(testCaseName);
		//testCaseRepository.save(tc);
		testCases.add(tc);
		module.setTestCases(testCases);
		moduleRepository.save(module);
		return  CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, testCaseName + " test case created");
	}

	@Override
	public ResponseEntity<String> deleteTestCase(String email, String projectCode, String moduleName, String testCaseName) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if (module==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email/project/module details if are correct");
		
		Set<TestCase> testCases= module.getTestCases();
		List<TestCase> matchingTestCases= testCases.stream().filter(t->t.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
		if (matchingTestCases.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, testCaseName + " test case does not exist");
		
		testCases.removeAll(matchingTestCases);
		module.setTestCases(testCases);
		moduleRepository.save(module);
		try {
			testCaseRepository.deleteAll(matchingTestCases);
		}catch(Exception e) {
			return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, testCaseName + " test case deregistered but exist in repository as it is linked with anotehr entity");
		}
		return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, testCaseName + " test case deleted");
	}

	@Override
	public ResponseEntity<String> reNameTestCase(String email, String projectCode, String moduleName, String oldTestCaseName, String newTestCaseName) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if (module==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email/project/module details if are correct");
		
		Set<TestCase> testCases= module.getTestCases();
		List<TestCase> matchingTestCases= testCases.stream().filter(t->t.getName().equalsIgnoreCase(oldTestCaseName)).collect(Collectors.toList());
		if (matchingTestCases.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, oldTestCaseName + " test case not exist");
		
		TestCase tc= matchingTestCases.get(0);
		tc.setName(newTestCaseName);
			testCaseRepository.save(tc);
		return CompactServiceImpl.reportResponse(HttpStatus.ACCEPTED, "Test case renamed");
	}

	@Override
	public ResponseEntity<String> getTestCase(String email, String projectCode, String moduleName) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if (module==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email/project/module details if are correct");
		
		return CompactServiceImpl.reportResponse(HttpStatus.OK, module.getTestCases());
	}

	@Override
	public ResponseEntity<String> getTestCase(String email, String projectCode, String moduleName, String testCaseName) {
		Module module= moduleServiceImpl.getModule(email, projectCode, moduleName);
		if (module==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email/project/module details if are correct");
		
		Set<TestCase> testCases= module.getTestCases();
		List<TestCase> matchingTestCases= testCases.stream().filter(t->t.getName().equalsIgnoreCase(testCaseName)).collect(Collectors.toList());
		if (matchingTestCases.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, testCaseName + " test case not exist");
		
		return CompactServiceImpl.reportResponse(HttpStatus.OK, matchingTestCases);
		
	}

	@Override
	public ResponseEntity<String> getTestCase(String email, String projectCode) {
		Set<Project> projects= projectServiceImpl.getProject(email, projectCode);
		if (projects==null)
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, "Please check the email and project details");
		Project project= projects.stream().findFirst().get();
		List<TestCase> tcs= new ArrayList<>();
		project.getModules().forEach(m-> tcs.addAll(m.getTestCases()));
		return CompactServiceImpl.reportResponse(HttpStatus.OK, tcs);
	}

}
