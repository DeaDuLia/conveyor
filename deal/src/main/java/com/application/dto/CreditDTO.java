package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    @NotNull
    private double amount;
    @NotNull
    private int term;
    @NotNull
    private double monthlyPayment;
    @NotNull
    private double psk;
    @NotNull
    private double rate;
    @NotNull
    private boolean isInsuranceEnabled;
    @NotNull
    private boolean isSalaryClient;
    @NotNull
    private List<PaymentScheduleElementDTO> paymentSchedule;
}
