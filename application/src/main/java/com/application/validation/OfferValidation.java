package com.application.validation;

import com.application.exception.DateIsTooSmallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.application.ApplicationConstants.MIN_AGE;
import static com.application.util.ApplicationUtils.getFullYears;

@Slf4j
@Component
public class OfferValidation{

    public void validAgeByMinValue(LocalDate birthDate) throws DateIsTooSmallException {
        int age = getFullYears(birthDate);
        if (age < MIN_AGE) {
            log.warn("Age < 18");
            throw new DateIsTooSmallException("Age < 18");
        }
    }
}
