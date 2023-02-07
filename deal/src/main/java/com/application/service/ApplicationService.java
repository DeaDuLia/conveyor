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

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application createAndSaveApplication(Client client) {
        log.info("create application Entity");
        Application application = new Application(0, client, null, null, null, null, null, 0, null);
        log.info("save application Entity");
        applicationRepository.save(application);
        return application;
    }

    public Application updateApplication(Application application, LoanOfferDTO loanOfferDTO) {
        log.info("update application params");
        updateApplicationStatus(application, ApplicationStatus.PREPARE_DOCUMENTS, ChangeType.AUTOMATIC)
                .setAppliedOffer(
                        new LoanOffer(loanOfferDTO.getApplicationId(), loanOfferDTO.getRequestedAmount(),
                        loanOfferDTO.getTotalAmount(), loanOfferDTO.getTerm(), loanOfferDTO.getMonthlyPayment(),
                        loanOfferDTO.getRate(), loanOfferDTO.isInsuranceEnabled(), loanOfferDTO.isSalaryClient())
                );
        return application;
    }
    public Application updateApplicationStatus (Application application, ApplicationStatus applicationStatus, ChangeType changeType) {
        log.info("update application status");
        application.setStatus(applicationStatus);
        application.getStatus_history().setStatus(applicationStatus);
        application.getStatus_history().setChangeType(changeType);
        application.getStatus_history().setTimeStamp(LocalDate.now());
        return application;
    }

    public LoanApplicationRequestDTO createApplicationDTO(LoanApplicationRequestDTO request, Client client) {
        log.info("calculate LoanApplicationRequestDTO");
        return new LoanApplicationRequestDTO(
                request.getAmount(), request.getTerm(), client.getFirstName(), client.getLastName(), client.getMiddleName(),
                client.getEmail(), client.getBirthDate(), client.getPassport().getSeries(),
                client.getPassport().getNumber()
        );
    }

    public Application getById (long applicationId) {
        log.info("get application by id");
        Application app =  applicationRepository.findById(applicationId).get();
        if (app.getStatus_history() == null) app.setStatus_history(new StatusHistory());
        return app;
    }

    public void saveApp (Application application) {
        log.info("save application Entity");
        applicationRepository.save(application);
    }
}
