package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanOfferServiceTest {

    @InjectMocks
    private LoanOfferService offerService;

    @Test
    void assignIdFromApplication() {
        List<LoanOfferDTO> loanOffers = new ArrayList<>();
        loanOffers.add(new LoanOfferDTO(0, 10000, 10000, 6, 3000, 10, false, false));
        loanOffers.add(new LoanOfferDTO(0, 10000, 10000, 6, 3000, 10, false, true));
        loanOffers.add(new LoanOfferDTO(0, 10000, 10000, 6, 3000, 10, true, false));
        loanOffers.add(new LoanOfferDTO(0, 10000, 10000, 6, 3000, 10, true, true));
        offerService.assignIdFromApplication(loanOffers, 10);
        assertEquals(4, loanOffers.size());
        assertEquals(10, loanOffers.get(0).getApplicationId());
    }
}