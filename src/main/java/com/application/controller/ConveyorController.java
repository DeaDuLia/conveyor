package com.application.controller;

import com.application.dto.CreditDTO;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.dto.ScoringDataDTO;
import com.application.service.CreditService;
import com.application.service.LoanOfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/conveyor")
@Api(description = "Контроллер для расчёта условий и параметров кредита")
public class ConveyorController {
    private final LoanOfferService loanOfferService;
    private final CreditService creditService;

    @PostMapping(value = "/offers")
    @ApiOperation("Расчёт возможных условий кредита")
    public List<LoanOfferDTO>offers (@RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO) throws Exception {
        log.info("calculation of loan conditions request");
        List<LoanOfferDTO> res = loanOfferService.getOffers(loanApplicationRequestDTO);
        log.info("calculation of loan conditions response");
        return res;
    }

    @PostMapping("/calculation")
    @ApiOperation("Полный расчет параметров кредита")
    public CreditDTO calculation (@RequestBody @Valid ScoringDataDTO scoringDataDTO) throws Exception {
        log.info("calculation of loan parameters request");
        CreditDTO creditDTO = creditService.getCredit(scoringDataDTO);
        log.info("calculation of loan parameters response");
        return creditDTO;
    }
}
