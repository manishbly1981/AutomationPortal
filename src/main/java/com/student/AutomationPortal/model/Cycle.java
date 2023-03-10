package com.student.AutomationPortal.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="cycle")
public class Cycle {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="cycleName", nullable= false, unique= true)
	private String releaseName;
	
	/*@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name= "releaseId", referencedColumnName = "id")
	private Release release;
	*/
}
