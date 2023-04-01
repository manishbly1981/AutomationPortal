package com.student.AutomationPortal.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="executionRelease" , uniqueConstraints = @UniqueConstraint(name= "releaseConst", columnNames = "name"))
//@ToString(exclude = {"cycles"})
public class ExecutionRelease {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false)
	private String name;
	@JsonManagedReference
	@OneToMany(mappedBy = "executionRelease",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Cycle> cycles;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;
}
