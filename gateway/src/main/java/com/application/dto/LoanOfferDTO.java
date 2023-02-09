package com.application.dto;
import lombok.*;

import javax.validation.constraints.NotNull;

import static com.application.GatawayConstants.DEFAULT_RATE;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanOfferDTO {
    @NotNull
    private long applicationId;
    @NotNull
    private double requestedAmount;
    @NotNull
    private double totalAmount;
    @NotNull
    private int term;
    @NotNull
    private double monthlyPayment;
    @NotNull
    private double rate = DEFAULT_RATE;
    @NotNull
    private boolean isInsuranceEnabled;
    @NotNull
    private boolean isSalaryClient;
}
