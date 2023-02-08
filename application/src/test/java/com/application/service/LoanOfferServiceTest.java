package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.exception.DateIsTooSmallException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LoanOfferServiceTest {
    @InjectMocks
    LoanOfferService loanOfferService;


    @ParameterizedTest
    @ValueSource(ints = {2022, 2000})
    void validData(int year) {
        LocalDate birthDate = LocalDate.of(year, 12, 10);
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO(
                10000, 6, "Ivan", "Ivanov", "Ivanovich", "name@mail.ru",
                birthDate, "4444", "444444"
        );
        try {
            loanOfferService.validData(request);
        } catch (Exception e) {
           assertEquals(e.getClass(), DateIsTooSmallException.class);
        }
    }
}