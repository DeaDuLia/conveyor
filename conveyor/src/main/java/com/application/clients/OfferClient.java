package com.application.clients;

import com.application.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "Conveyor", url = "localhost:8080")
public interface OfferClient {

    @RequestMapping(value = "/conveyor/offers", method = RequestMethod.POST)
    public List<LoanOfferDTO> offers (@RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO) throws Exception;

    @RequestMapping(value = "/conveyor/calculation", method = RequestMethod.POST)
    public CreditDTO calculation (@RequestBody @Valid ScoringDataDTO scoringDataDTO) throws Exception;

}
