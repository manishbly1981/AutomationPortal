package com.student.AutomationPortal.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="module" , uniqueConstraints = @UniqueConstraint(name= "moduleConst", columnNames = "name"))
@ToString(exclude = {"projects","TestCases"})
public class Module {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false)
	private String name;

	/*@OneToMany(cascade= CascadeType.ALL)
	@JoinTable(name="module_testCase", 
			joinColumns = @JoinColumn(name="moduleId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="testCaseId", referencedColumnName = "id"))
	private Set<TestCase> TestCases;
*/

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "projectId", referencedColumnName = "id")
	private Project projects;

	@JsonManagedReference
	@OneToMany(mappedBy = "modules",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TestCase> TestCases;
}
