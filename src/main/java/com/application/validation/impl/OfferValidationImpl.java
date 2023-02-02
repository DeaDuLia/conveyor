package com.application.validation.impl;

import com.application.validation.OfferValidation;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import static com.application.ApplicationConstants.MIN_AGE;
import static com.application.util.ApplicationUtils.getFullYears;

@Slf4j
public class OfferValidationImpl implements OfferValidation {

    @Override
    public void validAgeByMinValue(LocalDate birthDate) throws Exception {
        int age = getFullYears(birthDate);
        if (age < MIN_AGE) {
            log.warn("Age < 18");
            throw new Exception("Age < 18");
        }
    }
}
