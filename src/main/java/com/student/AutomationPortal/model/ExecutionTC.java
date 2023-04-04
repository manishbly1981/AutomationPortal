package com.student.AutomationPortal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="executionTC", uniqueConstraints = @UniqueConstraint(name= "uTestSeqPerCycle", columnNames = {"cycleId","tcSeq"}))
public class ExecutionTC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cycleId", referencedColumnName = "id")
    private Cycle cycle;
    private String moduleName;

    private String tcName;
    private int tcSeq;

    @JsonManagedReference
    @OneToMany(mappedBy = "executionTC",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExecutionTS> executionTS;

    @JsonManagedReference
    @OneToMany(mappedBy = "executionTc",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ExecutionSummaryResult> executionSummary;
}
