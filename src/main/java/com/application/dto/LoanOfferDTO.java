package com.application.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static com.application.ApplicationConstants.DEFAULT_RATE;
import static com.application.ApplicationConstants.INSURANCE_PRICE;


@Setter
@Getter
@NoArgsConstructor
public class LoanOfferDTO {
    private long applicationId;
    private double requestedAmount;
    private double totalAmount;
    private int term;
    private double monthlyPayment;
    private double rate = DEFAULT_RATE;
    private boolean isInsuranceEnabled;
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
