package com.application.service;


import com.application.dto.FinishRegistrationRequestDTO;
import com.application.dto.ScoringDataDTO;
import com.application.entity.Application;
import com.application.entity.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ScoringDataService {

    public ScoringDataDTO fullScoringData (FinishRegistrationRequestDTO request, Application application) {
        log.info("full scoring data from request and application");
        Client client = application.getClient();
        return new ScoringDataDTO(
                application.getAppliedOffer().getRequestedAmount(), application.getAppliedOffer().getTerm(), client.getFirstName(), client.getLastName(), client.getMiddleName(),
                request.getGender(), client.getBirthDate(), client.getPassport().getSeries(),
                client.getPassport().getNumber(), request.getPassportIssueDate(),
                request.getPassportIssueBrach(), request.getMaritalStatus(), request.getDependentAmount(),
                request.getEmployment(), request.getAccount(), application.getAppliedOffer().isInsuranceEnabled(), application.getAppliedOffer().isSalaryClient()
        );
    }
}
