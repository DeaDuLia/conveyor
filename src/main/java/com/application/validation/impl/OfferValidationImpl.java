package com.application.validation.impl;

import com.application.validation.OfferValidation;
import java.time.LocalDate;
import static com.application.ApplicationConstants.MIN_AGE;
import static com.application.util.ApplicationUtils.getFullYears;

public class OfferValidationImpl implements OfferValidation {

    @Override
    public void validAgeByMinValue(LocalDate birthDate) throws Exception {
        int age = getFullYears(birthDate);
        if (age < MIN_AGE) throw new Exception("Вам нет 18 лет");
    }
}
