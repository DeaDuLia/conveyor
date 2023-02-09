package com.application.entity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class LoanOffer {

    @Id
    @Column(name = "application_id", nullable = false)
    private long applicationId;

    @Column(name = "requested_amount")
    private double requestedAmount;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "term")
    private int term;

    @Column(name = "monthly_payment")
    private double monthlyPayment;

    @Column(name = "rate")
    private double rate;

    @Column(name = "insurance_enabled")
    private boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    private boolean isSalaryClient;
}
