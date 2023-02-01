package com.application.controller;

import com.application.dto.CreditDTO;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.dto.ScoringDataDTO;
import com.application.service.CreditService;
import com.application.service.LoanOfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(description = "Контроллер для расчёта условий и параметров кредита")
public class ApplicationController {
    private final LoanOfferService loanOfferService;
    private final CreditService creditService;

    @PostMapping(value = "/conveyor/offers")
    @ApiOperation("Расчёт возможных условий кредита")
    public ResponseEntity<List<LoanOfferDTO>> offers (@Valid LoanApplicationRequestDTO loanApplicationRequestDTO) throws Exception {
        System.out.println("calculation of loan conditions request");
        List<LoanOfferDTO> res = loanOfferService.getOffers(loanApplicationRequestDTO);
        System.out.println("calculation of loan conditions response");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/conveyor/calculation")
    @ApiOperation("Полный расчет параметров кредита")
    public ResponseEntity<CreditDTO> calculation (@Valid ScoringDataDTO scoringDataDTO) throws Exception {
        System.out.println("calculation of loan parameters request");
        CreditDTO creditDTO = creditService.getCredit(scoringDataDTO);
        System.out.println("calculation of loan parameters response");
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

}
