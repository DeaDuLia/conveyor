package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequestDTO {
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
    @Pattern(regexp = "[\\w.]{2,50}@[\\w.]{2,20}")
    private String email;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    @Pattern(regexp = "^\\d{4}")
    private String passportSeries;
    @NotNull
    @Pattern(regexp = "^\\d{6}")
    private String passportNumber;
}
