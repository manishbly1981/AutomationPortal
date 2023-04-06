package com.student.AutomationPortal.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.student.AutomationPortal.dataList.ExecutionStatusList;

import lombok.Data;

@Data
@Entity
@Table(name="executionSummary")
public class ExecutionSummaryResult {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "executionTcId", referencedColumnName = "id")
	private ExecutionTC executionTc;
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatusList status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionStartDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionEndDateTime;
}
