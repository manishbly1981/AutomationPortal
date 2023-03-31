package com.student.AutomationPortal.model;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="testCase")
@ToString(exclude = {"testSteps"})
public class TestCase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
}
