package com.application.validation;

import java.time.LocalDate;

public interface OfferValidation {
    void validAgeByMinValue(LocalDate birthDate) throws Exception;
}
