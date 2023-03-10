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
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="testCase")
public class TestCase {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false, unique= true)
	private String name;
	
	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="testCase_TestStep", 
			joinColumns = @JoinColumn(name="testCaseId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="testStepId", referencedColumnName = "id"))
	private Set<TestStep> TestSteps;
}
