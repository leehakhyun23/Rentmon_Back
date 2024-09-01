package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageRequest {
    private String userid;
    private String senderType;
    private String message;

    public MessageRequest() {}
}
