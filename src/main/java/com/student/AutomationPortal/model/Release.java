package com.student.AutomationPortal.model;

import javax.persistence.*;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="executionRelease" , uniqueConstraints = @UniqueConstraint(name= "releaseConst", columnNames = "name"))
public class Release {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable= false)
	private String name;

	@OneToMany(mappedBy = "release",cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, orphanRemoval = true)
	private List<Cycle> cycles;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "project_id", referencedColumnName = "id")
	private Project project;
}
