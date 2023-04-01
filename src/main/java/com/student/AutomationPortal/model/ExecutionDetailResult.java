package com.student.AutomationPortal.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.student.AutomationPortal.dataValidator.ExecutionStatus;

import lombok.Data;

@Data
@Entity
@Table(name="executionDetailResult")
public class ExecutionDetailResult {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	private int sNo;

	private String objective;

	private String expected;
	private String actual;
	private String screenshot; //server file path
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionStartDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionEndDateTime;
}
