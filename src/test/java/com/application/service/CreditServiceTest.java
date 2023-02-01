package com.application.service;

import com.application.dto.EmploymentDTO;
import com.application.dto.ScoringDataDTO;
import com.application.enums.EmploymentPosition;
import com.application.enums.EmploymentStatus;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @InjectMocks
    private CreditService creditService;

    @Test
    void getCredit() throws Exception {
        double amount= 10000;
        int term = 6;
        String name = "Petr";
        String lastName = "Ivanov";
        String middleName = "Vitalievich";
        Gender gender = Gender.MALE;
        LocalDate date = LocalDate.of(2000, 10, 12);
        String passSer = "4220";
        String passNum = "123456";
        LocalDate passportIssueDate = LocalDate.of(2000, 10, 12);
        String passportIssueBranch = "test";
        MaritalStatus maritalStatus = MaritalStatus.MARRIED;
        int dependentAmount = 2;
        EmploymentStatus employmentStatus = EmploymentStatus.SELF_EMPLOYED;
        String employerINN = "test";
        double salary = 20000;
        EmploymentPosition position = EmploymentPosition.MID_MANAGER;
        int workExperienceCurrent = 22;
        int workExperienceTotal = 22;
        String account = "test";
        boolean isInsuranceEnabled = true;
        boolean isSalaryClient = true;
        ScoringDataDTO dataDTO = new ScoringDataDTO(amount, term, name, lastName,
                middleName, gender, date, passSer, passNum, passportIssueDate,
                passportIssueBranch, maritalStatus, dependentAmount,
                new EmploymentDTO(employmentStatus, employerINN, salary, position, workExperienceTotal, workExperienceCurrent),
                account, isInsuranceEnabled, isSalaryClient);
        creditService.getCredit(dataDTO);
        assertEquals(1, 1);
    }
}