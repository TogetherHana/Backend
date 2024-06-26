package com.togetherhana.sharingAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SharingMemberResponse {
    private Long memberIdx;
    private String memberNickName;
    private String imageUrl;
    private Boolean isLeader;
}
