package com.student.AutomationPortal.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.student.AutomationPortal.model.User;
import com.student.AutomationPortal.model.Project;
import com.student.AutomationPortal.model.Module;

import com.student.AutomationPortal.repository.ModuleRepository;
import com.student.AutomationPortal.repository.ProjectRepository;
import com.student.AutomationPortal.repository.UserRepository;
import com.student.AutomationPortal.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService{
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired 
	ModuleRepository moduleRepository;
	
	
	@Override
	public ResponseEntity<String> registerModule(String email, String projectCode, String moduleName) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();

		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project has been assigned to user");
		
		List<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
		if(matchingProject==null||matchingProject.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " project has not been assigned to user");
		
		Set<Module> modules= matchingProject.get(0).getModules();
		List<Module> matchingModules = null;
		if(modules!=null)
			 matchingModules= modules.stream().filter(m->m.getName().equalsIgnoreCase(moduleName)).collect(Collectors.toList());
		if(matchingModules.size()>0 )
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, moduleName + " module is already assigned to user");
		if(moduleRepository.findByName(moduleName)!=null) {
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, moduleName + " module is already registered in another project");
		}
		Module m= new Module();
		m.setName(moduleName);
		modules.add(m);
		Project project=matchingProject.get(0);
		project.setModules(modules);
		projectRepository.save(project);
		return CompactServiceImpl.reportResponse(HttpStatus.FOUND, moduleName + " module is allocated to the project " + projectCode);
	}

	@Override
	public ResponseEntity<String> getModuleList() {
		return CompactServiceImpl.reportResponse(HttpStatus.OK, moduleRepository.findAll());
	}

	@Override
	public ResponseEntity<String> getModuleList(String email, String projectCode) {
//		User user= userRepository.findByEmail(email);
//		if (user==null)
//			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project has been assigned to user");
		
		List<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
		if(matchingProject==null||matchingProject.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " project has not been assigned to user");
		
		return CompactServiceImpl.reportResponse(HttpStatus.OK, matchingProject.get(0).getModules());
	}

	@Override
	public ResponseEntity<String> deRegisterModule(String email, String projectCode, String moduleName) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project has been assigned to user");
		
		List<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
		if(matchingProject==null||matchingProject.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " project has not been assigned to user");
		
		Set<Module> modules= matchingProject.get(0).getModules();
		List<Module> matchingModules = null;
		if(modules!=null)
			 matchingModules= modules.stream().filter(m->m.getName().equalsIgnoreCase(moduleName)).collect(Collectors.toList());
		if(matchingModules.size()<=0 )
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, moduleName + " module is already deregistered");

		modules.removeAll(matchingModules);
		Project p= matchingProject.get(0);
		p.setModules(modules);
		projectRepository.save(p);
		//moduleRepository.deleteAll(matchingModules);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, moduleName + " module is deRegistered from the project " + projectCode);
	}


	//Method to be used across the framework internally
	public Module getModule(String email, String projectCode, String moduleName) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();
		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return null;
		
		List<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
		if(matchingProject==null||matchingProject.size()<=0)
			matchingProject= projects.stream().filter(p-> p.getProjectName().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
			if(matchingProject==null||matchingProject.size()<=0)
				return null;
		
		Set<Module> modules= matchingProject.get(0).getModules();
		List<Module> matchingModules= modules.stream().filter(m->m.getName().equalsIgnoreCase(moduleName)).collect(Collectors.toList());
		if (matchingModules.size()>1)
			return null;
		else if(matchingModules.size()<0)
			return null;
		else
			return matchingModules.get(0);
	}

	@Override
	public ResponseEntity<String> deleteModule(String email, String projectCode, String moduleName) {
		Optional<User> oUSer = userRepository.findByEmail(email);
		oUSer.orElseThrow(()->{
			CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, email+ " email is not registered");
			return new UsernameNotFoundException(email + " not found");});
		User user= oUSer.get();

		Set<Project> projects= user.getProjects();
		if(projects==null|| projects.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, "No project has been assigned to user");
		
		List<Project> matchingProject= projects.stream().filter(p-> p.getProjectCode().equalsIgnoreCase(projectCode)||p.getProjectName().equalsIgnoreCase(projectCode)).collect(Collectors.toList());
		if(matchingProject==null||matchingProject.size()<=0)
			return CompactServiceImpl.reportResponse(HttpStatus.NOT_FOUND, projectCode + " project has not been assigned to user");
		
		Set<Module> modules= matchingProject.get(0).getModules();
		List<Module> matchingModules = null;
		if(modules!=null)
			 matchingModules= modules.stream().filter(m->m.getName().equalsIgnoreCase(moduleName)).collect(Collectors.toList());
		if(matchingModules.size()<=0 )
			return CompactServiceImpl.reportResponse(HttpStatus.FOUND, moduleName + " module is already deregistered");

		modules.removeAll(matchingModules);
		Project p= matchingProject.get(0);
		p.setModules(modules);
		projectRepository.save(p);
		moduleRepository.deleteAll(matchingModules);
		return CompactServiceImpl.reportResponse(HttpStatus.OK, moduleName + " module is deRegistered from the project " + projectCode);
	}

}
