package com.application.controller;

import com.application.clients.ApplicationClient;
import com.application.clients.DealClient;
import com.application.dto.EmailMessage;
import com.application.dto.FinishRegistrationRequestDTO;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
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
@Api(description = "Контроллер gateway")
public class GatewayController {
    private final ApplicationClient applicationClient;
    private final DealClient dealClient;

    @PostMapping(value = "/application")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public List<LoanOfferDTO>  application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception {
        return applicationClient.application(request);
    }

    @PostMapping(value = "/application/apply")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public void  application_apply  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        applicationClient.offer(loanOfferDTO);
    }

    @PostMapping(value = "/application/registration/{applicationId}")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public void  application_registration (@RequestBody @Valid FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable long applicationId) throws Exception {
        dealClient.calculate(finishRegistrationRequestDTO, applicationId);
    }

    @PostMapping(value = "/document/{applicationId}")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public void  document_create (@PathVariable long applicationId) {
        dealClient.document_send(applicationId);
    }

    @PostMapping(value = "/document/{applicationId}/sign")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public void document_sign (@PathVariable long applicationId) {
        dealClient.document_sign(applicationId);
    }
    
    @PostMapping(value = "/document/{applicationId}/code")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public void document_code (@PathVariable long applicationId) {
        dealClient.document_code(applicationId);
    }
}
