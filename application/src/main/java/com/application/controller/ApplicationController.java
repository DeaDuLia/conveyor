package com.application.controller;

import com.application.clients.DealClient;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.service.LoanOfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(description = "Контроллер Application")
public class ApplicationController {
    private final DealClient dealClient;
    private final LoanOfferService loanOfferService;

    // [+] По API приходит LoanApplicationRequestDTO
    // [+] На основе LoanApplicationRequestDTO происходит прескоринг.
    // [+] Отправляется POST-запрос на /deal/application в МС deal через FeignClient.
    // [+] Ответ на API - список из 4х LoanOfferDTO от "худшего" к "лучшему".
    @PostMapping(value = "/application")
    @ApiOperation("Прескоринг + запрос на расчёт возможных условий кредита")
    public List<LoanOfferDTO>  application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception {
        loanOfferService.validData(request);
        return dealClient.application(request);
    }

    // [+] По API приходит LoanOfferDTO
    // [+] Отправляется POST-запрос на /deal/offer в МС deal через FeignClient.
    @PutMapping(value = "/application/offer")
    @ApiOperation("Выбор одного из предложений")
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception {
        dealClient.offer(loanOfferDTO);
    }
}
