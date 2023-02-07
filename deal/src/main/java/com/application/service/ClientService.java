package com.application.service;

import com.application.dto.LoanApplicationRequestDTO;
import com.application.entity.Client;
import com.application.entity.Passport;
import com.application.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public Client createAndSaveClient(LoanApplicationRequestDTO request) {
        log.info("create client Entity");
        Client client = new Client(0, request.getLastName(), request.getFirstName(), request.getMiddleName(),
                request.getBirthdate(), request.getEmail(), null, null, 0,
                new Passport(0, request.getPassportSeries(), request.getPassportNumber(), null, null),
                null, null);
        log.info("save client Entity");
        clientRepository.save(client);
        return client;
    }

    public void save (Client client) {
        log.info("save client Entity");
        clientRepository.save(client);
    }

}
