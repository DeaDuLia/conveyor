package com.application.service;


import com.application.dto.CreditDTO;
import com.application.dto.EmploymentDTO;
import com.application.dto.PaymentScheduleElement;
import com.application.dto.ScoringDataDTO;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.application.util.ApplicationUtils.getFullYears;

@Service
public class CreditService {
    @Value("${dayOfMonth}")
    private int dayOfMonth;
    @Value("${empl.minWorkExperienceTotal}")
    private int minWorkExperienceTotal;
    @Value("${empl.minWorkExperienceCurrent}")
    private int minWorkExperienceCurrent;
    @Value("${credit.minYear}")
    private int minYear;
    @Value("${credit.maxYear}")
    private int maxYear;
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

    public CreditDTO getCredit (ScoringDataDTO scoringDataDTO) throws Exception {
        double rate = calculateRate(scoringDataDTO);
        double psk = scoringDataDTO.getAmount() + scoringDataDTO.getAmount()*(rate/100);
        double monthlyPayment = psk / scoringDataDTO.getTerm();
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        LocalDate nowDate = LocalDate.now();
        for (int i = 0; i < scoringDataDTO.getTerm(); i++) {
            int num = i+1;
            LocalDate date = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue()+num, dayOfMonth);
            double totalPayment = num * monthlyPayment;
            double remainingDebt = psk - totalPayment;
            paymentSchedule.add(new PaymentScheduleElement(num, date, totalPayment, 0, 0, remainingDebt));
        }
        return new CreditDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), monthlyPayment, rate, psk, scoringDataDTO.isInsuranceEnabled(), scoringDataDTO.isSalaryClient(), paymentSchedule);
    }

    private double calculateRate(ScoringDataDTO scoringData) throws Exception {
        double rate = defaultRate;
        EmploymentDTO employment = scoringData.getEmployment();
        rate += calculateRateFromEmployment(employment);
        if (scoringData.getAmount() > employment.getSalary()*20) throw new Exception("Отказ");
        rate += calculateRateFromMaritalStatus(scoringData.getMaritalStatus());
        if (scoringData.getDependentAmount() > 1) rate+=1;
        rate += calculateRateFromBirthDateAndGender(scoringData.getBirthdate(), scoringData.getGender());
        return rate;
    }
    private double calculateRateFromEmployment (EmploymentDTO employment) throws Exception {
        double rate = 0;
        switch (employment.getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new Exception("Отказ");
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
        if (employment.getWorkExperienceTotal() < minWorkExperienceTotal
                || employment.getWorkExperienceCurrent() < minWorkExperienceCurrent) throw new Exception("Отказ");
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
        if (age < minYear || age > maxYear) throw new Exception("Отказ по причине возраста");
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
}
