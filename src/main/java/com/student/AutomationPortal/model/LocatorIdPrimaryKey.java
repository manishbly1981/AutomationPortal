package com.student.AutomationPortal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Embeddable
@Data
public class LocatorIdPrimaryKey implements Serializable{
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="sNo")
	private int sNo;

	/*public LocatorIdPrimaryKey(Long id, int sNo) {
		this.id= id;
		this.sNo= sNo;
	}
	*/
}
