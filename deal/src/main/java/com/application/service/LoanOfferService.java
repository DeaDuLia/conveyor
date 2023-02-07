package com.application.service;

import com.application.dto.LoanOfferDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class LoanOfferService {

    public void assignIdFromApplication (List<LoanOfferDTO> offers, long id) {
        log.info("assign id from Application");
        for (LoanOfferDTO offer : offers) offer.setApplicationId(id);
    }
}
