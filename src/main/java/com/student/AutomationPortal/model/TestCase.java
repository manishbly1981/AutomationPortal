package com.student.AutomationPortal.model;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="testCase")
@ToString(exclude = {"testSteps","modules"})
public class TestCase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

/*
	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="testCase_TestStep", 
			joinColumns = @JoinColumn(name="testCaseId",referencedColumnName= "id"),//
			inverseJoinColumns = @JoinColumn(name="testStepId", referencedColumnName = "id"))
	private List<TestStep> TestSteps;
*/

	@JsonManagedReference
	@OneToMany(mappedBy = "testCases",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TestStep> testSteps;


	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "moduleId", referencedColumnName = "id")
	private Module modules;
}
