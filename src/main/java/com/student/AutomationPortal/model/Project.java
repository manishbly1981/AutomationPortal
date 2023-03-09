package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="projectCode", nullable= false, unique= true)
	private String projectCode;
	
	@Column(name="projectName", nullable= false, unique= true)
	private String projectName;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinTable(name="project_module", 
			joinColumns = @JoinColumn(name="projectId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="moduleId", referencedColumnName = "id"))
	private Set<Module> modules;
}
