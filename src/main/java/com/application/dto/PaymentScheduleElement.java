package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentScheduleElement {
    private int number;
    @NotNull
    private LocalDate date;
    private double totalPayment;
    private double interestPayment;
    private double debtPayment;
    private double remainingDebt;
}
