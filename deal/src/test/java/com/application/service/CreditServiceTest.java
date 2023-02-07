package com.application.service;

import com.application.dto.CreditDTO;
import com.application.entity.Credit;
import com.application.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {
    @InjectMocks
    private CreditService creditService;
    @Mock
    CreditRepository creditRepository;

    @Test
    void createAndSaveCredit() {
        CreditDTO creditDTO = new CreditDTO(10000, 6, 0, 0, 10, false, false,
                new ArrayList<>());
        Credit credit = creditService.createAndSaveCredit(creditDTO);
        assertEquals(creditDTO.getAmount(), credit.getAmount());
        assertEquals(creditDTO.getTerm(), credit.getTerm());
        assertEquals(creditDTO.getMonthlyPayment(), credit.getMonthlyPayment());
        assertEquals(creditDTO.getPsk(), credit.getPsk());
        assertEquals(creditDTO.getRate(), credit.getRate());
        assertEquals(creditDTO.isInsuranceEnabled(), credit.isInsurance());
        assertEquals(creditDTO.isSalaryClient(), credit.isSalaryClient());
    }
}