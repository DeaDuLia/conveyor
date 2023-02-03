package com.application.entity;

import com.application.enums.EmploymentPosition;
import com.application.enums.EmploymentStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employment_id", nullable = false)
    private long employmentId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    @Column(name = "employer_inn", nullable = false)
    private String employerInn;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "salary", nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentPosition position;

    @Column(name = "work_experience_total", nullable = false)
    private int workExperienceTotal;

    @Column(name = "work_experience_current", nullable = false)
    private int workExperienceCurrent;
}
