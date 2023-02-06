package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.entity.*;
import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import com.application.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;
    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    void updateApplication() {
        Application application = new Application(0, new Client(), new Credit(), ApplicationStatus.APPROVED, null, null, null,
                0, new StatusHistory());
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        Application res = applicationService.updateApplication(application, loanOfferDTO);
        assertEquals(ApplicationStatus.PREPARE_DOCUMENTS, res.getStatus());
        assertEquals(ApplicationStatus.PREPARE_DOCUMENTS, res.getStatus_history().getStatus());
    }

    @Test
    void updateApplicationStatus() {
        Application application = new Application(0, new Client(), new Credit(), ApplicationStatus.APPROVED, null, null, null,
                0, new StatusHistory());
        Application res = applicationService.updateApplicationStatus(application, ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC);
        assertEquals(ApplicationStatus.PREAPPROVAL, res.getStatus());
        assertEquals(ApplicationStatus.PREAPPROVAL, res.getStatus_history().getStatus());
        assertEquals(ChangeType.AUTOMATIC, res.getStatus_history().getChangeType());
    }

    @Test
    void createApplicationDTO() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO(10000, 6, "Ivanv", "Ivan", "Ivanovich",
                "name@mail.ru", LocalDate.now(), "4444", "444444");
        Client client = new Client(0, "Ivanov", "Ivan", "Ivanovich", LocalDate.now(), "name@mail.ru",
                Gender.MALE, MaritalStatus.MARRIED, 2, new Passport(0, "4444", "444444",
                "text", LocalDate.now()), new Employment(), "text");
        LoanApplicationRequestDTO res = applicationService.createApplicationDTO(request, client);
        assertEquals(res.getAmount(), request.getAmount());
        assertEquals(res.getTerm(), request.getTerm());
        assertEquals(res.getFirstName(), client.getFirstName());
        assertEquals(res.getLastName(), client.getLastName());
        assertEquals(res.getMiddleName(), client.getMiddleName());
        assertEquals(res.getEmail(), client.getEmail());
        assertEquals(res.getBirthdate(), client.getBirthDate());
        assertEquals(res.getPassportSeries(), client.getPassport().getSeries());
        assertEquals(res.getPassportNumber(), client.getPassport().getNumber());
    }
}