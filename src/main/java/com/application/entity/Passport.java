package com.application.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "passport_id", nullable = false)
    private long passport_id;

    @Column(name = "status", nullable = false)
    private String series;

    @Column(name = "status", nullable = false)
    private String number;

    @Column(name = "issue_branch", nullable = false)
    private String issueBranch;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
}
