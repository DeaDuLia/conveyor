package com.application.service;


import com.application.dto.CreditDTO;
import com.application.dto.EmploymentDTO;

import com.application.dto.PaymentScheduleElementDTO;
import com.application.dto.ScoringDataDTO;
import com.application.entity.Credit;
import com.application.entity.PaymentScheduleElement;
import com.application.enums.CreditStatus;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import com.application.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.application.ApplicationConstants.*;
import static com.application.util.ApplicationUtils.getFullDaysBetweenDates;
import static com.application.util.ApplicationUtils.getFullYears;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditService {
    @Value("${empl.minWorkExperienceTotal}")
    private int minWorkExperienceTotal;
    @Value("${empl.minWorkExperienceCurrent}")
    private int minWorkExperienceCurrent;
    @Value("${credit.minFemaleYear}")
    private int minFemYear;
    @Value("${credit.maxFemaleYear}")
    private int maxFemYear;
    @Value("${credit.minMaleYear}")
    private int minMaleYear;
    @Value("${credit.maxMaleYear}")
    private int maxMaleYear;
    @Value("${global.defaultRate}")
    private double defaultRate;
    private final CreditRepository creditRepository;

    public CreditDTO getCredit (ScoringDataDTO scoringDataDTO) throws Exception {
        log.info("calculate rate");
        double rate = calculateRate(scoringDataDTO);
        LocalDate nowDate = LocalDate.now();
        log.info("calculate loan conditions");
        List<PaymentScheduleElementDTO> paymentScheduleElements = calculatePaymentSchedule(scoringDataDTO.getAmount(), rate, scoringDataDTO.getTerm(), nowDate);
        double monthlyPayment = paymentScheduleElements.get(0).getDebtPayment();
        double psk = 0;
        for (PaymentScheduleElementDTO tmp : paymentScheduleElements) {psk += tmp.getTotalPayment();}
        return new CreditDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), monthlyPayment, psk, rate,
                scoringDataDTO.isInsuranceEnabled(), scoringDataDTO.isSalaryClient(), paymentScheduleElements);
    }

    public Credit createAndSaveCredit(CreditDTO creditDTO) {
        log.info("create credit Entity");
        List<PaymentScheduleElement> paymentScheduleElements = new ArrayList<>();
        for (PaymentScheduleElementDTO tmp : creditDTO.getPaymentSchedule()) {
            paymentScheduleElements.add(new com.application.entity.PaymentScheduleElement(
                    0L, tmp.getNumber(), tmp.getDate(), tmp.getTotalPayment(),
                    tmp.getInterestPayment(), tmp.getDebtPayment(), tmp.getRemainingDebt()
            ));
        }
        Credit credit = new Credit(0, creditDTO.getAmount(), creditDTO.getTerm(),
                creditDTO.getMonthlyPayment(), creditDTO.getRate(), creditDTO.getPsk(),
                paymentScheduleElements, creditDTO.isInsuranceEnabled(), creditDTO.isSalaryClient(), CreditStatus.CALCULATED);
        log.info("save credit Entity");
        creditRepository.save(credit);
        return credit;
    }

    private double calculateRate(ScoringDataDTO scoringData) throws Exception {
        double rate = defaultRate;
        EmploymentDTO employment = scoringData.getEmployment();
        rate += calculateRateFromEmployment(employment);
        if (scoringData.getAmount() > employment.getSalary()*20) {
            log.warn("denied, т.к. amount > salary x 20");
            throw new Exception("denied, т.к. amount > salary x 20");
        }
        rate += calculateRateFromMaritalStatus(scoringData.getMaritalStatus());
        if (scoringData.getDependentAmount() > 1) rate+=1;
        rate += calculateRateFromBirthDateAndGender(scoringData.getBirthdate(), scoringData.getGender());
        return rate;
    }
    private double calculateRateFromEmployment (EmploymentDTO employment) throws Exception {
        double rate = 0;
        switch (employment.getEmploymentStatus()) {
            case UNEMPLOYED:
                log.warn("denied, UNEMPLOYED");
                throw new Exception("denied, UNEMPLOYED");
            case SELF_EMPLOYED:
                rate+=1;
                break;
            case BUSINESS_OWNER:
                rate+=3;
                break;
        }
        switch (employment.getPosition()) {
            case MID_MANAGER:
                rate-=2;
                break;
            case TOP_MANAGER:
                rate-=4;
                break;
        }
        if (employment.getWorkExperienceTotal() < minWorkExperienceTotal || employment.getWorkExperienceCurrent() < minWorkExperienceCurrent) {
            log.warn("denied, Work Experience < Min");
            throw new Exception("denied, Work Experience < Min");
        }
        return rate;
    }
    private double calculateRateFromMaritalStatus(MaritalStatus maritalStatus) {
        double rate = 0;
        switch (maritalStatus) {
            case MARRIED:
                rate-=3;
                break;
            case DIVORCED:
                rate+=1;
                break;
        }
        return rate;
    }
    private double calculateRateFromBirthDateAndGender (LocalDate birthDate, Gender gender) throws Exception {
        int age = getFullYears(birthDate);
        double rate = 0;
        if (age < MIN_CREDIT_AGE || age > MAX_CREDIT_AGE) {
            log.warn("denied, age < min or age > max");
            throw new Exception("denied, age < min or age > max");
        }
        switch (gender) {
            case FEMALE:
                if (age >= minFemYear && age <= maxFemYear) {
                    rate-=3;
                }
                break;
            case MALE:
                if (age >= minMaleYear && age <= maxMaleYear) {
                    rate-=3;
                }
                break;
            case NON_BINARY:
                rate+=3;
                break;
        }
        return rate;
    }

    private List<PaymentScheduleElementDTO> calculatePaymentSchedule (double amount, double rate, int term, LocalDate startDate) {
        List<PaymentScheduleElementDTO> paymentSchedule = new ArrayList<>();
        LocalDate nextDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
        double remainingDebt = amount;
        double debtPayment = amount / term;
        for (int num = 1; num < term+1; num++) {
            LocalDate tmp = LocalDate.of(nextDate.plusMonths(1).getYear(), nextDate.plusMonths(1).getMonthValue(), PAYMENT_DAY);
            int days = getFullDaysBetweenDates(nextDate, tmp);
            nextDate = tmp;
            double interestPayment = remainingDebt * (rate/100) * (days/365.0);
            double totalPayment = debtPayment + interestPayment;

            remainingDebt-=totalPayment;
            paymentSchedule.add(new PaymentScheduleElementDTO(num, LocalDate.of(nextDate.getYear(), nextDate.getMonthValue(), nextDate.getDayOfMonth()),
                    totalPayment, interestPayment, debtPayment, Math.max(remainingDebt, 0)));
        }
        return paymentSchedule;
    }
}
