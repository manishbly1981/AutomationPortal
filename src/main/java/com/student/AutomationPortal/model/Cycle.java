package com.student.AutomationPortal.model;


import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="cycle" , uniqueConstraints = @UniqueConstraint(name= "cycle_const", columnNames = "cycleName"))
public class Cycle {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="cycleName", nullable= false)
	private String releaseName;
	
	/*@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name= "releaseId", referencedColumnName = "id")
	private Release release;
	*/
}
