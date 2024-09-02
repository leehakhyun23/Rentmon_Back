package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatInfoDto {
    private int crseq;
    private int unreadMessages;

    public ChatInfoDto() {}
}
