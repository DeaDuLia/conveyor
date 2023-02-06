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
    @Column(name = "employment_id", nullable = false)
    private long employmentId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    @Column(name = "employer_inn")
    private String employerInn;

    @Column(name = "salary")
    private double salary;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private EmploymentPosition position;

    @Column(name = "work_experience_total")
    private int workExperienceTotal;

    @Column(name = "work_experience_current")
    private int workExperienceCurrent;
}
