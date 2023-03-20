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
	private int sNo; //how to ensure it is not duplicate in test case
	private String stepDescription;
	private String action;
	private String logicalName;

	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="TS_LR", 
			joinColumns = @JoinColumn(name="testStepId",referencedColumnName= "id"),//
			inverseJoinColumns = {@JoinColumn(name="locatorId", referencedColumnName = "id"),
								 @JoinColumn(name="locatorSNo", referencedColumnName = "sNo")})
	private Set<Locators> locatorId;
	
	private String value; //if value is in curly bracket means parameterize from test Data, else hard coded
	private String exitIfFail;
	
	
}
