package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="testStep")
@ToString(exclude = {"testCases"})
public class TestStep {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="seq", nullable= false)
	private int seq;
	@Column(name="objective", nullable= false)
	private String objective;
	@Column(name="expected", nullable= false)
	private String expected;
	@Column(name="action", nullable= false)
	private String action;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="TS_LR", 
			joinColumns = @JoinColumn(name="testStepId",referencedColumnName= "id"),//
			inverseJoinColumns = {@JoinColumn(name="locatorId", referencedColumnName = "id")
								})
	private Set<Locators> locators;

	private String value; //if value is in curly bracket means parameterize from test Data, else hard coded
	private String exitIfFail;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "testCaseId", referencedColumnName = "id")
	private TestCase testCases;

}
