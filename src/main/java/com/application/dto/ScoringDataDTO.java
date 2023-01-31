package com.application.dto;

import com.application.enums.Gender;
import com.application.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScoringDataDTO {
    @Min(10000)
    private double amount;
    @Min(6)
    private int term;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{2,30}")
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[A-Za-z]{2,30}")
    private String lastName;
    @Pattern(regexp = "^[A-Za-z]{2,30}")
    private String middleName;
    @NotNull
    private Gender gender;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    @Pattern(regexp = "^\\d{4}")
    private String passportSeries;
    @NotNull
    @Pattern(regexp = "^\\d{6}")
    private String passportNumber;
    @NotNull
    private LocalDate passportIssueDate;
    @NotNull
    @NotBlank
    private String passportIssueBranch;
    @NotNull
    private MaritalStatus maritalStatus;
    private int dependentAmount;
    @NotNull
    private EmploymentDTO employment;
    @NotNull
    @NotBlank
    private String account;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}
