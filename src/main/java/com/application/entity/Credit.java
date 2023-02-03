package com.application.entity;

import com.application.enums.CreditStatus;

import javax.persistence.*;

@Entity
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "credit_id", nullable = false)
    private long creditId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "term", nullable = false)
    private int term;

    @Column(name = "monthly_payment", nullable = false)
    private double monthlyPayment;

    @Column(name = "rate", nullable = false)
    private double rate;

    @Column(name = "psk", nullable = false)
    private double psk;

        //payment_schedule jsonb ???

    @Column(name = "insurance_enable", nullable = false)
    private boolean isInsurance;

    @Column(name = "salary_client", nullable = false)
    private boolean isSalaryClient;

    @Column(name = "credit_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
}
