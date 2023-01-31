package com.application.controller;

import com.application.dto.CreditDTO;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.dto.ScoringDataDTO;
import com.application.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.application.service.LoanOfferService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApplicationController {
    private final LoanOfferService loanOfferService;
    private final CreditService creditService;

    @RequestMapping("/")
    public ResponseEntity<List<String>> index_get () {
        List<String> name = new ArrayList<>();
        name.add("Hello!");
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @PostMapping(value = "/conveyor/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LoanOfferDTO>> offers (@Valid LoanApplicationRequestDTO loanApplicationRequestDTO) throws Exception {
        List<LoanOfferDTO> res = loanOfferService.getOffers(loanApplicationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/conveyor/calculation")
    public ResponseEntity<CreditDTO> calculation (@Valid ScoringDataDTO scoringDataDTO) throws Exception {
        CreditDTO creditDTO = creditService.getCredit(scoringDataDTO);
        return new ResponseEntity<>(creditDTO, HttpStatus.OK);
    }

}
