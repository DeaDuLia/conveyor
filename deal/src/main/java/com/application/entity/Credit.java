package com.application.entity;

import com.application.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private long creditId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "term")
    private int term;

    @Column(name = "monthly_payment")
    private double monthlyPayment;

    @Column(name = "rate")
    private double rate;

    @Column(name = "psk")
    private double psk;

    @Column(name = "payment_schedule")
    @Type(type = "jsonb")
    private List<PaymentScheduleElement> paymentSchedule;

    @Column(name = "insurance_enable")
    private boolean isInsurance;

    @Column(name = "salary_client")
    private boolean isSalaryClient;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
}
