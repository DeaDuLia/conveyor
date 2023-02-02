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
    @NotNull
    private double totalPayment;
    @NotNull
    private double interestPayment;
    @NotNull
    private double debtPayment;
    @NotNull
    private double remainingDebt;
}
