package com.student.AutomationPortal.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="executionRelease" , uniqueConstraints = @UniqueConstraint(name= "releaseConst", columnNames = "releaseName"))
public class Release {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="releaseName", nullable= false)
	private String releaseName;
}
