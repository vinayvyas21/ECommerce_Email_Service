package com.vk.email.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}