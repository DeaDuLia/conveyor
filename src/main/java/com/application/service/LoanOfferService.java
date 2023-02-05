package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.validation.impl.OfferValidationImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LoanOfferService {

    public List<LoanOfferDTO> getOffers (LoanApplicationRequestDTO request) throws Exception {
        new OfferValidationImpl().validAgeByMinValue(request.getBirthdate());
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        log.info("Calculating...");
        loanOffers.add(new LoanOfferDTO(request.getAmount(), request.getTerm(), false, false));
        loanOffers.add(new LoanOfferDTO(request.getAmount(), request.getTerm(), false, true));
        loanOffers.add(new LoanOfferDTO(request.getAmount(), request.getTerm(), true, false));
        loanOffers.add(new LoanOfferDTO(request.getAmount(), request.getTerm(), true, true));
        return loanOffers;
    }




}
