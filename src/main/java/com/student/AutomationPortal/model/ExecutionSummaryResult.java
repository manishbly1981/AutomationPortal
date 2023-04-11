package com.student.AutomationPortal.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.student.AutomationPortal.dataList.ExecutionStatusList;

import lombok.Data;

@Data
@Entity
@Table(name="executionSummaryResult")
public class ExecutionSummaryResult {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
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

	@JsonManagedReference
	@OneToMany(mappedBy = "executionSummaryResults",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ExecutionDetailResult> executionDetailResults;

}
