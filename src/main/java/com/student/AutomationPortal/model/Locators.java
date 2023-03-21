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
@Table(name="LocatorRepository")
public class Locators {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", nullable= false)
	private Long id;
	
	@Column(name="seq", nullable= false)
	private int seq;

	@Column(name="page", nullable= false)
	private String page;
	private String logicalName;
	private String locatorType;
	private String locatorValue;
	private int priority;
	
}
