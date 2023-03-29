package com.student.AutomationPortal.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.student.AutomationPortal.dataValidator.ExecutionStatus;

import lombok.Data;

@Data
@Entity
@Table(name="executionSummary")
public class ExecutionSummary{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="releaseId",referencedColumnName= "id")
	private ExecutionRelease releases;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="cycleId",referencedColumnName = "id")
	private Cycle cycles;
	
	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="executionTestCase", 
	joinColumns = @JoinColumn(name="executionId",referencedColumnName= "id"),//
	inverseJoinColumns = @JoinColumn(name="TestCaseId", referencedColumnName = "id"))
	private Set<TestCase> testCases;

	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionStartDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionEndDateTime;
}
