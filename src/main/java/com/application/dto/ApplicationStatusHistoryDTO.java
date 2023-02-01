package com.application.dto;

import com.application.enums.ApplicationStatus;
import lombok.*;
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
    private ApplicationStatus changeType;
}



