package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="project" , uniqueConstraints = {@UniqueConstraint(name= "pCodeConst", columnNames = "projectCode"),@UniqueConstraint(name= "pNameConst", columnNames = "projectName")})
public class Project {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="projectCode", nullable= false)
	private String projectCode;
	
	@Column(name="projectName", nullable= false)
	private String projectName;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinTable(name="project_module", 
			joinColumns = @JoinColumn(name="projectId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="moduleId", referencedColumnName = "id"))
	private Set<Module> modules;
}
