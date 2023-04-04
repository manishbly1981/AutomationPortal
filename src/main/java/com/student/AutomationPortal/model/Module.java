package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Entity
@Data
@Table(name="module" , uniqueConstraints = @UniqueConstraint(name= "moduleConst", columnNames = "name"))
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
	@JsonManagedReference
	@OneToMany(mappedBy = "modules",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TestCase> TestCases;


}
