package com.application.service;


import com.application.dto.CreditDTO;
import com.application.dto.EmploymentDTO;

import com.application.dto.PaymentScheduleElementDTO;
import com.application.dto.ScoringDataDTO;
import com.application.entity.Credit;
import com.application.entity.PaymentScheduleElement;
import com.application.enums.CreditStatus;
import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import com.application.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreditService {
    @Value("${empl.minWorkExperienceTotal}")
    private int minWorkExperienceTotal;
    @Value("${empl.minWorkExperienceCurrent}")
    private int minWorkExperienceCurrent;
    @Value("${credit.minFemaleYear}")
    private int minFemYear;
    @Value("${credit.maxFemaleYear}")
    private int maxFemYear;
    @Value("${credit.minMaleYear}")
    private int minMaleYear;
    @Value("${credit.maxMaleYear}")
    private int maxMaleYear;
    @Value("${global.defaultRate}")
    private double defaultRate;
    private final CreditRepository creditRepository;

    public Credit createAndSaveCredit(CreditDTO creditDTO) {
        log.info("create credit Entity");
        List<PaymentScheduleElement> paymentScheduleElements = new ArrayList<>();
        for (PaymentScheduleElementDTO tmp : creditDTO.getPaymentSchedule()) {
            paymentScheduleElements.add(new com.application.entity.PaymentScheduleElement(
                    0L, tmp.getNumber(), tmp.getDate(), tmp.getTotalPayment(),
                    tmp.getInterestPayment(), tmp.getDebtPayment(), tmp.getRemainingDebt()
            ));
        }
        Credit credit = new Credit(0, creditDTO.getAmount(), creditDTO.getTerm(),
                creditDTO.getMonthlyPayment(), creditDTO.getRate(), creditDTO.getPsk(),
                paymentScheduleElements, creditDTO.isInsuranceEnabled(), creditDTO.isSalaryClient(), CreditStatus.CALCULATED);
        log.info("save credit Entity");
        creditRepository.save(credit);
        return credit;
    }
}
