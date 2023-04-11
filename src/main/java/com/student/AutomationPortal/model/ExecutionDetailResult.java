package com.student.AutomationPortal.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.student.AutomationPortal.dataList.ExecutionStatusList;

import lombok.Data;

@Data
@Entity
@Table(name="executionDetailResult")
public class ExecutionDetailResult {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	private int sNo;

	private String objective;

	private String expected;
	private String actual;
	private String screenshot; //server file path
	@Enumerated(EnumType.ORDINAL)
	private ExecutionStatusList status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionStartDateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ExecutionEndDateTime;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
	@JoinColumn(name = "executionSummaryId", referencedColumnName = "id")
	private ExecutionSummaryResult executionSummaryResults;
}
