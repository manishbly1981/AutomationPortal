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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="module")
public class Module {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false, unique= true)
	private String name;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinTable(name="module_testCase", 
			joinColumns = @JoinColumn(name="moduleId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="testCaseId", referencedColumnName = "id"))
	private Set<TestCase> TestCases;
	
	
}
