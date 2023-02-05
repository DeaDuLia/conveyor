package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.entity.Application;
import com.application.entity.Client;
import com.application.entity.LoanOffer;
import com.application.entity.StatusHistory;
import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.application.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application createApplication (Client client) {
        Application application = new Application(0, client, null, null, null, null, null, 0, null);
        applicationRepository.save(application);
        return application;
    }

    public void updateApplication(Application application, LoanOfferDTO loanOfferDTO) {
        updateApplicationStatus(application, ApplicationStatus.PREPARE_DOCUMENTS, ChangeType.AUTOMATIC);
        application.setAppliedOffer(
                new LoanOffer(loanOfferDTO.getApplicationId(), loanOfferDTO.getRequestedAmount(),
                        loanOfferDTO.getTotalAmount(), loanOfferDTO.getTerm(), loanOfferDTO.getMonthlyPayment(),
                        loanOfferDTO.getRate(), loanOfferDTO.isInsuranceEnabled(), loanOfferDTO.isSalaryClient())
        );

        applicationRepository.save(application);
    }
    public void updateApplicationStatus (Application application, ApplicationStatus applicationStatus, ChangeType changeType) {
        application.setStatus(applicationStatus);
        application.getStatus_history().setStatus(applicationStatus);
        application.getStatus_history().setChangeType(changeType);
        application.getStatus_history().setTimeStamp(LocalDate.now());
        applicationRepository.save(application);
    }

    public LoanApplicationRequestDTO createApplicationDTO(LoanApplicationRequestDTO request, Client client) {
        return new LoanApplicationRequestDTO(
                request.getAmount(), request.getTerm(), client.getFirstName(), client.getLastName(), client.getMiddleName(),
                client.getEmail(), client.getBirthDate(), client.getPassport().getSeries(),
                client.getPassport().getNumber()
        );
    }

    public Application getById (long applicationId) {
        Application app = applicationRepository.getById(applicationId);
        if (app.getStatus_history() == null) app.setStatus_history(new StatusHistory());
        return app;
    }
}
