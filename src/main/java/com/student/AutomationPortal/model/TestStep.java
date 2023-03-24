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
@Table(name="testStep")
public class TestStep {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="seq", nullable= false)
	private int seq;
	@Column(name="stepDescription", nullable= false)
	private String stepDescription;
	@Column(name="action", nullable= false)
	private String action;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="TS_LR", 
			joinColumns = @JoinColumn(name="testStepId",referencedColumnName= "id"),//
			inverseJoinColumns = {@JoinColumn(name="locatorId", referencedColumnName = "id")
								})
	private Set<Locators> locator;

	private String value; //if value is in curly bracket means parameterize from test Data, else hard coded
	private String exitIfFail;
}
