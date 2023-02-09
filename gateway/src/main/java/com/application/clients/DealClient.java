package com.application.clients;

import com.application.dto.EmailMessage;
import com.application.dto.FinishRegistrationRequestDTO;
import com.application.dto.LoanApplicationRequestDTO;
import com.application.dto.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "deal", url = "localhost:8081")
public interface DealClient {

    @RequestMapping(value = "/deal/calculate/{applicationId}", method = RequestMethod.PUT)
    public void calculate  (@RequestBody @Valid FinishRegistrationRequestDTO finishRegistrationRequestDTO, @PathVariable long applicationId) throws Exception;

    @RequestMapping(value = "/deal/document/{applicationId}/send", method = RequestMethod.POST)
    public void document_send (@PathVariable long applicationId);

    @RequestMapping(value = "/deal/document/{applicationId}/sign", method = RequestMethod.POST)
    public void document_sign (@PathVariable long applicationId);

    @RequestMapping(value = "/deal/document/{applicationId}/code", method = RequestMethod.POST)
    public void document_code (@PathVariable long applicationId);
}
