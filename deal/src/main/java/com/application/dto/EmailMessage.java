package com.application.dto;

import com.application.enums.MessageTheme;
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
    private MessageTheme theme;
    @NotNull
    private long applicationId;
}
