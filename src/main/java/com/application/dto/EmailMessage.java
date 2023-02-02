package com.application.dto;

import com.application.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {
    @NotNull
    private String address;
    @NotNull
    private ApplicationStatus theme;
    @NotNull
    private long applicationId;
}
