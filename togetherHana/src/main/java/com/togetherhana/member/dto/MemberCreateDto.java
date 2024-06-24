package com.togetherhana.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCreateDto {

    private String name;

    private String nickname;

    private Long accountNumber;

    private String phoneNumber;

    private String address;

}
