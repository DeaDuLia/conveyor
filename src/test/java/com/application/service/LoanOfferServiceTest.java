package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanOfferServiceTest {

    @InjectMocks
    private LoanOfferService offerService;

    @Test
    void getOffers() throws Exception {
        double amount = 10000;
        int term = 6;
        String name = "Petr";
        String lastName = "Ivanov";
        String middleName = "Vitalievich";
        String email = "name@mail.ru";
        LocalDate date = LocalDate.of(2000, 10, 12);
        String passSer = "4220";
        String passNum = "123456";
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO(amount, term, name, lastName, middleName,
                email, date, passSer, passNum);
        List<LoanOfferDTO> offers = offerService.getOffers(request);
        assertEquals(4, offers.size());

        assertEquals(11000, offers.get(0).getTotalAmount());
        assertEquals(10, offers.get(0).getRate());
        assertEquals(10900, offers.get(1).getTotalAmount());
        assertEquals(9, offers.get(1).getRate());
        assertEquals(110700, offers.get(2).getTotalAmount());
        assertEquals(7, offers.get(2).getRate());
        assertEquals(110600, offers.get(3).getTotalAmount());
        assertEquals(6, offers.get(3).getRate());
    }
}