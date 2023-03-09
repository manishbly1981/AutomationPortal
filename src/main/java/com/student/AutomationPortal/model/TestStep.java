package com.student.AutomationPortal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String stepDescription;
	private String action;
	private String logicalName;
	private String locatorId;
	private String value; //if value is in curly bracket means parameterize from test Data, else hard coded
	private String exitIfFail;
	
	
}
