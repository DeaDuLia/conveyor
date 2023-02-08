package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.exception.DateIsTooSmallException;
import com.application.validation.OfferValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class LoanOfferService {
    private final OfferValidation offerValidation;

    public void validData (LoanApplicationRequestDTO request) throws DateIsTooSmallException {
        offerValidation.validAgeByMinValue(request.getBirthdate());
    }
}
