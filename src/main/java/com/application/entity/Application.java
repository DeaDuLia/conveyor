package com.application.entity;

import com.application.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;


import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "application_id", nullable = false)
    private long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    // applied_offer jsonb ????

    @Column(name = "sign_date", nullable = false)
    private LocalDate signDate;

    //ses_code ???

    @Column
    @JsonSubTypes.Type(value =  JsonBinaryType.class, name = "jsonb")
    private StatusHistory status_history;
}
