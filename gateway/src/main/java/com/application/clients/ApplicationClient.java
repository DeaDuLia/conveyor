package com.application.clients;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "application", url = "localhost:8082")
public interface ApplicationClient {

    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public List<LoanOfferDTO> application  (@RequestBody @Valid LoanApplicationRequestDTO request) throws Exception;

    @RequestMapping(value = "/application/offer", method = RequestMethod.PUT)
    public void offer  (@RequestBody @Valid LoanOfferDTO loanOfferDTO) throws Exception;

}
