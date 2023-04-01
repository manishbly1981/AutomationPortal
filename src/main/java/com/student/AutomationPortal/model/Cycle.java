package com.student.AutomationPortal.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="cycle" , uniqueConstraints = @UniqueConstraint(name= "cycle_const", columnNames = "name"))
public class Cycle {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false)
	private String name;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "executionRelease_id", referencedColumnName = "id")
	private ExecutionRelease executionRelease;

	@JsonManagedReference
	@OneToMany(mappedBy = "cycle",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ExecutionTC> executionTC;
}
