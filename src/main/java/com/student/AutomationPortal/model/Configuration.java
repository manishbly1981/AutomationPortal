package com.student.AutomationPortal.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name="Configuration")
public class Configuration {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	private String Name;
	private String value;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name= "projectId", referencedColumnName = "id")
	private Project projectId;
}
