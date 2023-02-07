package com.application.clients;

import com.application.dto.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "deal", url = "localhost:8081")
public interface DealClient {

    @RequestMapping(value = "/deal/application", method = RequestMethod.POST)
    public List<LoanOfferDTO> application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception;

    @RequestMapping(value = "/deal/offer", method = RequestMethod.PUT)
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception;

}
