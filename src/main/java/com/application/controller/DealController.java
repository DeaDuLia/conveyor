package com.application.controller;


import com.application.clients.OfferClient;
import com.application.dto.*;
import com.application.entity.*;
import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.application.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/deal")
@Api(description = "Контроллер для оформления сделок")
public class DealController {
    private final ApplicationService applicationService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final LoanOfferService loanOfferService;
    private final ScoringDataService scoringDataService;
    private final OfferClient offerClient;

    @PostMapping(value = "/application")
    @ApiOperation("расчёт возможных условий кредита")
    public List<LoanOfferDTO> application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception {
        log.info("deal application calculation request");
        Client client = clientService.createAndSaveClient(request);
        long appId =  applicationService.createAndSaveApplication(client).getApplicationId();
        LoanApplicationRequestDTO loanApplicationRequestDTO = applicationService.createApplicationDTO(request, client);
        log.info("send POST request to /conveyor/offers");
        List<LoanOfferDTO> offers = offerClient.offers(loanApplicationRequestDTO);
        loanOfferService.assignIdFromApplication(offers, appId);
        log.info("deal application calculation response");
        return offers;
    }

    @PutMapping(value = "/offer")
    @ApiOperation("Выбор одного из предложений")
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        log.info("deal offer calculation request");
        Application application = applicationService.getById(loanOfferDTO.getApplicationId());
        applicationService.updateApplication(application, loanOfferDTO);
        applicationService.saveApp(application);
    }

    @PutMapping(value = "/calculate/{applicationId}")
    @ApiOperation("завершение регистрации + полный подсчёт кредита")
    public void calculate  (@RequestBody @Valid FinishRegistrationRequestDTO finishRegistrationRequestDTO,  @PathVariable long applicationId) throws Exception {
        log.info("deal application update request");
        Application application = applicationService.getById(applicationId);
        ScoringDataDTO scoringDataDTO = scoringDataService.fullScoringData(finishRegistrationRequestDTO, application);
        log.info("send POST request to /conveyor/calculation");
        CreditDTO creditDTO = offerClient.calculation(scoringDataDTO);
        creditService.createAndSaveCredit(creditDTO);
        applicationService.updateApplicationStatus(application, ApplicationStatus.DOCUMENT_CREATED, ChangeType.AUTOMATIC);
        applicationService.saveApp(application);
    }
}
