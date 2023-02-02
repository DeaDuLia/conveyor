package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.application.ApplicationConstants.PAYMENT_DAY;
import static com.application.util.ApplicationUtils.getFullDaysBetweenDates;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {
    private double amount;
    private int term;
    private double monthlyPayment;
    private double psk;
    private double rate;

    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;

    public CreditDTO (double amount, int term, double rate, LocalDate startDate, boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.info("enter data: amount, term, rate");
        this.amount = amount;
        this.term = term;
        this.rate = rate;
        this.psk = 0;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        log.info("Calculate PSK,monthly payment and interest payment");
        calculatePaymentSchedule(amount, rate, term, startDate);
    }

    private void calculatePaymentSchedule (double amount, double rate, int term, LocalDate startDate) {
        paymentSchedule = new ArrayList<>();
        LocalDate nextDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
        double remainingDebt = amount;
        double debtPayment = amount / term;
        monthlyPayment = debtPayment;
        for (int num = 1; num < term+1; num++) {
            LocalDate tmp = LocalDate.of(nextDate.plusMonths(1).getYear(), nextDate.plusMonths(1).getMonthValue(), PAYMENT_DAY);
            int days = getFullDaysBetweenDates(nextDate, tmp);
            nextDate = tmp;
            double interestPayment = remainingDebt * (rate/100) * (days/365.0);
            double totalPayment = debtPayment + interestPayment;
            psk+=totalPayment;
            remainingDebt-=totalPayment;
            paymentSchedule.add(new PaymentScheduleElement(num, LocalDate.of(nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth()),
                    totalPayment, interestPayment, debtPayment, Math.max(remainingDebt, 0)));
        }
    }
}
