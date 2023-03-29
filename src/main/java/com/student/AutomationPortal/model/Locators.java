package com.student.AutomationPortal.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;


@Entity
@Data
@Table(name="LocatorRepository")//, uniqueConstraints = {@UniqueConstraint(name= "uniqueConst", columnNames = {"seq","page","logicalName"})}
public class Locators {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", nullable= false)
	private Long id;
	
	@Column(name="seq", nullable= false)
	private int seq;
	@Column(name="page", nullable= false)
	private String page;
	@Column(name="logicalName", nullable= false)
	private String logicalName;
	@Column(name="locatorType", nullable= false)
	private String locatorType;
	@Column(name="locatorValue", nullable= false)
	private String locatorValue;
	@Column(name="priority", nullable= false)
	private int priority;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;
}
