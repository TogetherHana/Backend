package com.togetherhana.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String name;

    private String nickname;

    private Long accountNumber;

    private String phoneNumber;

    private String address;

}
