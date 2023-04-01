package com.student.AutomationPortal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="executionTS")
public class ExecutionTS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//, cascade = CascadeType.ALL
    @JoinColumn(name = "executionTcId", referencedColumnName = "id")
    private ExecutionTC executionTC;
    private int stepSeq;

    private String objective;
    private String expected;
    private String action;
    private String value;
    private String exitOnFail;
    private String locatorPage;
    private String locatorLogicalName;
    private String locatorType;
    private String locatorValue;

}
