package com.togetherhana.sharingAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParticipateRequest {
    private Long sharingAccountIdx;
    private String invitationCode;
}
