package com.student.AutomationPortal.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="project" , uniqueConstraints = {@UniqueConstraint(name= "pCodeConst", columnNames = "projectCode"),@UniqueConstraint(name= "pNameConst", columnNames = "projectName")})
@ToString(exclude = {"releases"})
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

	@JsonManagedReference
	@OneToMany(mappedBy = "project",cascade = CascadeType.ALL, fetch = FetchType.LAZY)//, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,CascadeType.REFRESH
	private List<ExecutionRelease> releases;
}
