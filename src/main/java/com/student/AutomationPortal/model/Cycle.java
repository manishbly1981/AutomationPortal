package com.student.AutomationPortal.model;


import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name="cycle" , uniqueConstraints = @UniqueConstraint(name= "cycle_const", columnNames = "name"))
public class Cycle {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false)
	private String name;


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "executionRelease_id")
	private Release release;

}
