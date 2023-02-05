package com.application.controller;


import com.application.clients.OfferClient;
import com.application.dto.*;
import com.application.entity.*;
import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.application.service.ApplicationService;
import com.application.service.ClientService;
import com.application.service.CreditService;
import com.application.service.ScoringDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/deal")
public class DealController {
    private final ApplicationService applicationService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final ScoringDataService scoringDataService;


    private final OfferClient offerClient;

    @PostMapping(value = "/application")
    public List<LoanOfferDTO> application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception {
        //По API приходит LoanApplicationRequestDTO
        //На основе LoanApplicationRequestDTO создаётся сущность Client и сохраняется в БД.
        Client client = clientService.createClient(request);
        //Создаётся Application со связью на только что созданный Client и сохраняется в БД.
        applicationService.createApplication(client);
        /*
        Отправляется POST запрос на /conveyor/offers МС conveyor через FeignClient (здесь и далее вместо FeignClient можно использовать RestTemplate).
        Каждому элементу из списка List<LoanOfferDTO> присваивается id созданной заявки (Application)
        Ответ на API - список из 4х LoanOfferDTO от "худшего" к "лучшему".
         */
        LoanApplicationRequestDTO loanApplicationRequestDTO = applicationService.createApplicationDTO(request, client);
        return offerClient.offers(loanApplicationRequestDTO);
    }

    @PutMapping(value = "/offer")
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        //По API приходит LoanOfferDTO
        //Достаётся из БД заявка(Application) по applicationId из LoanOfferDTO.
        Application application = applicationService.getById(loanOfferDTO.getApplicationId());
        /*
        В заявке обновляется статус, история статусов(List<ApplicationStatusHistoryDTO>),
        принятое предложение LoanOfferDTO устанавливается в поле appliedOffer.
         */
        //Заявка сохраняется.
        applicationService.updateApplication(application, loanOfferDTO);
    }

    @PutMapping(value = "/calculate/{applicationId}")
    public void calculate  (@RequestBody @Valid FinishRegistrationRequestDTO finishRegistrationRequestDTO,  @PathVariable long applicationId) throws Exception {
        //По API приходит объект FinishRegistrationRequestDTO и параметр applicationId (\).
        //Достаётся из БД заявка(Application) по applicationId.
        Application application = applicationService.getById(applicationId);
        //ScoringDataDTO насыщается информацией из FinishRegistrationRequestDTO и Client, который хранится в Application
        ScoringDataDTO scoringDataDTO = scoringDataService.fullScoringData(finishRegistrationRequestDTO, application);
        //Отправляется POST запрос к МС КК с телом ScoringDataDTO
        CreditDTO calculation = offerClient.calculation(scoringDataDTO);
        creditService.createCredit(calculation);
        applicationService.updateApplicationStatus(application, ApplicationStatus.DOCUMENT_CREATED, ChangeType.AUTOMATIC);
    }





}
