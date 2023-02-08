package com.application.controller;


import com.application.clients.OfferClient;
import com.application.dto.*;
import com.application.entity.*;
import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.application.enums.MessageTheme;
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

    // [+] По API приходит LoanApplicationRequestDTO
    // [+] На основе LoanApplicationRequestDTO создаётся сущность Client и сохраняется в БД.
    // [+] Создаётся Application со связью на только что созданный Client и сохраняется в БД.
    // [+] Отправляется POST запрос на /conveyor/offers МС conveyor через FeignClient
    // [+] Каждому элементу из списка List<LoanOfferDTO> присваивается id созданной заявки (Application)
    // [+] Ответ на API - список из 4х LoanOfferDTO от "худшего" к "лучшему".
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

    // [+] По API приходит LoanOfferDTO
    // [+] Достаётся из БД заявка(Application) по applicationId из LoanOfferDTO.
    // [+] В заявке обновляется статус, история статусов(List<ApplicationStatusHistoryDTO>),
    // [+] принятое предложение LoanOfferDTO устанавливается в поле appliedOffer
    // [+] Заявка сохраняется.
    @PutMapping(value = "/offer")
    @ApiOperation("Выбор одного из предложений")
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        log.info("deal offer calculation request");
        Application application = applicationService.getById(loanOfferDTO.getApplicationId());
        applicationService.updateApplication(application, loanOfferDTO);
        applicationService.saveApp(application);
    }

    // [+] По API приходит объект FinishRegistrationRequestDTO и параметр applicationId (Long).
    // [+] Достаётся из БД заявка(Application) по applicationId.
    // [+] ScoringDataDTO насыщается информацией из FinishRegistrationRequestDTO и Client, который хранится в Application
    // [+] Отправляется POST запрос к МС КК с телом ScoringDataDTO
    // [+] Создаём и сохраняем Credit
    // [+] Обновляем у заявки статус и историю статусов на DOCUMENT_CREATED
    // [+] Заявка сохраняется
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

    @PostMapping(value = "/document/{applicationId}/send")
    @ApiOperation("запрос на отправку документов")
    public void document_send (@RequestBody @Valid EmailMessage email, @PathVariable long applicationId) throws Exception {
        log.info("test1");

        log.info("test1 ok");
    }

    @PostMapping(value = "/document/{applicationId}/sign")
    @ApiOperation("запрос на подписание документов")
    public void document_sign (@RequestBody @Valid EmailMessage email, @PathVariable long applicationId) throws Exception {
        log.info("test2");

        log.info("test2 ok");
    }

    @PostMapping(value = "/document/{applicationId}/code")
    @ApiOperation("подписание документов")
    public void document_code (@RequestBody @Valid EmailMessage email, @PathVariable long applicationId) throws Exception {
        log.info("test3");

        log.info("test3 ok");
    }

}
