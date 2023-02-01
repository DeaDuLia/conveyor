package com.application.service;


import com.application.dto.CreditDTO;
import com.application.dto.EmploymentDTO;
import com.application.dto.ScoringDataDTO;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import static com.application.ApplicationConstants.MAX_CREDIT_AGE;
import static com.application.ApplicationConstants.MIN_CREDIT_AGE;
import static com.application.util.ApplicationUtils.getFullYears;

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

    public CreditDTO getCredit (ScoringDataDTO scoringDataDTO) throws Exception {
        System.out.println("calculate rate");
        double rate = calculateRate(scoringDataDTO);
        LocalDate nowDate = LocalDate.now();
        System.out.println("calculate loan conditions");
        return new CreditDTO(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), rate, nowDate, scoringDataDTO.isInsuranceEnabled(), scoringDataDTO.isSalaryClient());
    }

    private double calculateRate(ScoringDataDTO scoringData) throws Exception {
        double rate = defaultRate;
        EmploymentDTO employment = scoringData.getEmployment();
        rate += calculateRateFromEmployment(employment);
        if (scoringData.getAmount() > employment.getSalary()*20) {
            System.out.println("denied, т.к. amount > salary x 20");
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
                System.out.println("denied, UNEMPLOYED");
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
            System.out.println("denied, Work Experience < Min");
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
            System.out.println("denied, age < min or age > max");
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
}
