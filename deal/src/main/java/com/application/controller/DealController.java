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
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, String> template;

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
    //>[+] Отправить Email на MC dossier через Кафу
    @PutMapping(value = "/offer")
    @ApiOperation("Выбор одного из предложений")
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        log.info("deal offer calculation request");
        Application application = applicationService.getById(loanOfferDTO.getApplicationId());
        applicationService.updateApplication(application, loanOfferDTO);
        applicationService.saveApp(application);
        // Здесь первое письмо
        EmailMessage email = new EmailMessage(application.getClient().getEmail(), MessageTheme.FINISH_REGISTRATION, 1);
        template.send(email.getTheme().topic, email.getAddress());
    }

    // [+] По API приходит объект FinishRegistrationRequestDTO и параметр applicationId (Long).
    // [+] Достаётся из БД заявка(Application) по applicationId.
    // [+] ScoringDataDTO насыщается информацией из FinishRegistrationRequestDTO и Client, который хранится в Application
    // [+] Отправляется POST запрос к МС КК с телом ScoringDataDTO
    // [+] Создаём и сохраняем Credit
    // [+] Обновляем у заявки статус и историю статусов на DOCUMENT_CREATED
    // [+] Заявка сохраняется
    //>[+]Отправить Email на MC dossier через Кафу
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
        EmailMessage email = new EmailMessage(application.getClient().getEmail(), MessageTheme.CREATE_DOCUMENTS, 1);
        template.send(email.getTheme().topic, email.getAddress());
    }

    @PostMapping(value = "/document/{applicationId}/send")
    @ApiOperation("запрос на отправку документов")
    public void document_send (@PathVariable long applicationId) {
        Application application = applicationService.getById(applicationId);
        EmailMessage msg = new EmailMessage(application.getClient().getEmail(), MessageTheme.SEND_DOCUMENTS, applicationId);
        log.info("send msg to MC dossier (Kafka) with email: " + msg.getAddress());
        msg.setApplicationId(applicationId);
        template.send(msg.getTheme().topic, msg.getAddress());
        log.info("complete sending");
    }

    @PostMapping(value = "/document/{applicationId}/sign")
    @ApiOperation("запрос на подписание документов")
    public void document_sign (@PathVariable long applicationId) {
        Application application = applicationService.getById(applicationId);
        EmailMessage msg = new EmailMessage(application.getClient().getEmail(), MessageTheme.SEND_SES, applicationId);
        log.info("send msg to MC dossier (Kafka) with email: " + msg.getAddress());
        msg.setApplicationId(applicationId);
        template.send(msg.getTheme().topic, msg.getAddress());
        log.info("complete sending");
    }

    @PostMapping(value = "/document/{applicationId}/code")
    @ApiOperation("подписание документов")
    public void document_code (@PathVariable long applicationId) {
        Application application = applicationService.getById(applicationId);
        EmailMessage msg = new EmailMessage(application.getClient().getEmail(), MessageTheme.CREDIT_ISSUED, applicationId);
        log.info("send msg to MC dossier (Kafka) with email: " + msg.getAddress());
        msg.setApplicationId(applicationId);
        template.send(msg.getTheme().topic, msg.getAddress());
        log.info("complete sending");
    }

    @GetMapping(value = "/admin/application/{applicationId}")
    @ApiOperation("подписание документов")
    public Application get_app_byId (@PathVariable long applicationId) {
        log.info("get app by id " + applicationId);
        return applicationService.getById(applicationId);
    }

    @GetMapping(value = "/admin/application")
    @ApiOperation("подписание документов")
    public List<Application> get_apps () {
        log.info("get all apps");
        return applicationService.getAll();
    }
}
