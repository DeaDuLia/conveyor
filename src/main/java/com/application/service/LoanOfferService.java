package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import com.application.validation.impl.OfferValidationImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.application.ApplicationConstants.DEFAULT_RATE;
import static com.application.ApplicationConstants.INSURANCE_PRICE;

@Slf4j
@Service
@AllArgsConstructor
public class LoanOfferService {

    public List<LoanOfferDTO> getOffers (LoanApplicationRequestDTO request) throws Exception {
        new OfferValidationImpl().validAgeByMinValue(request.getBirthdate());
        return createOffers(request);
    }

    public void assignIdFromApplication (List<LoanOfferDTO> offers, long id) {
        log.info("assign id from Application");
        for (LoanOfferDTO offer : offers) offer.setApplicationId(id);
    }

    private List<LoanOfferDTO> createOffers (LoanApplicationRequestDTO request) {
        log.info("Calculating...");
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        loanOffers.add(createOfferByTrueFalse(request.getAmount(), request.getTerm(), false, false));
        loanOffers.add(createOfferByTrueFalse(request.getAmount(), request.getTerm(), false, true));
        loanOffers.add(createOfferByTrueFalse(request.getAmount(), request.getTerm(), true, false));
        loanOffers.add(createOfferByTrueFalse(request.getAmount(), request.getTerm(), true, true));
        return loanOffers;
    }
    private LoanOfferDTO createOfferByTrueFalse (double requestedAmount, int term, boolean isInsuranceEnabled, boolean isSalaryClient) {
       double totalAmount = 0;
       double rate = DEFAULT_RATE;
        if (isInsuranceEnabled) {
            totalAmount += INSURANCE_PRICE;
            rate-=3;
        }
        if (isSalaryClient) {
            rate-=1;
        }
        totalAmount += requestedAmount + requestedAmount*(rate/100);
        double monthlyPayment = totalAmount / term;
        return new LoanOfferDTO(0, requestedAmount, totalAmount, term, monthlyPayment, rate, isInsuranceEnabled, isSalaryClient);
    }
}
