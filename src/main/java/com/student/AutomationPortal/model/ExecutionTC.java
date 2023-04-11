package com.student.AutomationPortal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="executionTC", uniqueConstraints = @UniqueConstraint(name= "uTestSeqPerCycle", columnNames = {"cycleId","tcSeq"}))
@ToString(exclude = {"cycles"})
public class ExecutionTC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cycleId", referencedColumnName = "id")
    private Cycle cycles;
    private String moduleName;

    private String tcName;
    private int tcSeq;

    @JsonManagedReference
    @OneToMany(mappedBy = "executionTCs",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExecutionTS> executionTS;

    @JsonManagedReference
    @OneToMany(mappedBy = "executionTc",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExecutionSummaryResult> executionSummary;
}
