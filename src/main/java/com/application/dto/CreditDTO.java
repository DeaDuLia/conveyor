package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    private double amount;
    private int term;
    private double monthlyPayment;
    private double rate;
    private double psk;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;
}
