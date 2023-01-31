package com.application.dto;

import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinishRegistrationRequestDTO {
    @NotNull
    private Gender gender;
    @NotNull
    private MaritalStatus maritalStatus;
    private int dependentAmount;
    @NotNull
    private LocalDate passportIssueDate;
    @NotNull
    private String passportIssueBrach;
    @NotNull
    private EmploymentDTO employment;
    @NotNull
    private String account;
}
