package com.application.entity;

import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "client_id", nullable = false)
    private long clientId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount", nullable = false)
    private int dependentAmount;

    @Column
    @JsonSubTypes.Type(value =  JsonBinaryType.class, name = "jsonb")
    private Passport passport;

    @Column
    @JsonSubTypes.Type(value =  JsonBinaryType.class, name = "jsonb")
    private Employment employment;

    @Column(name = "account", nullable = false)
    private String account;
}
