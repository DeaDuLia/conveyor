package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MsgDTO {
    @NotNull
    private String address;
    @NotNull
    private String title;
    @NotNull
    private String body;

}
