package com.application.dto;
import lombok.*;

import javax.validation.constraints.NotNull;

import static com.application.ApplicationConstants.DEFAULT_RATE;
import static com.application.ApplicationConstants.INSURANCE_PRICE;


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

    public LoanOfferDTO (double requestedAmount, int term, boolean isInsuranceEnabled, boolean isSalaryClient) {
        this.requestedAmount = requestedAmount;
        this.term = term;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        if (isInsuranceEnabled) {
            this.totalAmount += INSURANCE_PRICE;
            this.rate-=3;
        }
        if (isSalaryClient) {
            this.rate-=1;
        }
        this.totalAmount += this.requestedAmount + requestedAmount*(this.rate/100);
        this.monthlyPayment = totalAmount / term;
    }
}
