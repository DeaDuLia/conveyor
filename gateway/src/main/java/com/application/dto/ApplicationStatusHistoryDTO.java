package com.application.dto;

import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatusHistoryDTO {
    @NotNull
    private ApplicationStatus status;
    @NotNull
    private LocalDateTime time;
    @NotNull
    private ChangeType changeType;
}



