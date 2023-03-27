package com.student.AutomationPortal.model;

import javax.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name="Configuration")
public class Configuration {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	private String name;
	private String value;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name= "projectId", referencedColumnName = "id", nullable = false)
	private Project project;
}
