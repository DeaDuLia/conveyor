package com.application.service;

import com.application.dto.EmploymentDTO;
import com.application.dto.FinishRegistrationRequestDTO;
import com.application.dto.ScoringDataDTO;
import com.application.entity.*;
import com.application.enums.ApplicationStatus;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ScoringDataServiceTest {

    @InjectMocks
    private ScoringDataService scoringDataService;

    @Test
    void fullScoringData() {
        FinishRegistrationRequestDTO request = new FinishRegistrationRequestDTO(Gender.MALE, MaritalStatus.MARRIED,
                2, LocalDate.now(), "text", new EmploymentDTO(), "text");
        Client client = new Client(0, "Ivanov", "Ivan", "Ivanovich", LocalDate.now(), "name@mail.ru",
                Gender.MALE, MaritalStatus.MARRIED, 2, new Passport(0, "4444", "444444",
                "text", LocalDate.now()), new Employment(), "text");
        Application application = new Application(0, client, new Credit(), ApplicationStatus.APPROVED, null,
                new LoanOffer(0, 10000, 1000, 6, 3000, 10, false, false),
                null,
                0, new StatusHistory());
        ScoringDataDTO res = scoringDataService.fullScoringData(request, application);
        assertEquals(res.getAmount(), application.getAppliedOffer().getRequestedAmount());
        assertEquals(res.getTerm(), application.getAppliedOffer().getTerm());
        assertEquals(res.getFirstName(), client.getFirstName());
        assertEquals(res.getLastName(), client.getLastName());
        assertEquals(res.getMiddleName(), client.getMiddleName());
        assertEquals(res.getGender(), request.getGender());
        assertEquals(res.getBirthdate(), client.getBirthDate());
        assertEquals(res.getPassportSeries(), client.getPassport().getSeries());
        assertEquals(res.getPassportNumber(), client.getPassport().getNumber());
        assertEquals(res.getPassportIssueDate(), request.getPassportIssueDate());
        assertEquals(res.getMaritalStatus(), request.getMaritalStatus());
        assertEquals(res.getDependentAmount(), request.getDependentAmount());
        assertEquals(res.getEmployment(), request.getEmployment());
        assertEquals(res.getAccount(), request.getAccount());
        assertEquals(res.isInsuranceEnabled(), application.getAppliedOffer().isInsuranceEnabled());
        assertEquals(res.isSalaryClient(), application.getAppliedOffer().isSalaryClient());
    }
}