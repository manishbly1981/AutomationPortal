package com.student.AutomationPortal.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="LocatorRepository")
//@IdClass(LocatorId.class)
public class Locators {
	/*
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Id
	private int sNo;
	*/
	@EmbeddedId
	private LocatorIdPrimaryKey id;
	private String locatorName;
	private String locatorValue;
	private String priority;
	
	
}
