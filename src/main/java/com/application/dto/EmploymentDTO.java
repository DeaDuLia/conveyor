package com.application.dto;

import com.application.enums.EmploymentPosition;
import com.application.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDTO {
    @NotNull
    private EmploymentStatus employmentStatus;
    @NotNull
    private String employerINN;
    private double salary;
    @NotNull
    private EmploymentPosition position;
    @NotNull
    private int workExperienceTotal;
    @NotNull
    private int workExperienceCurrent;
}
