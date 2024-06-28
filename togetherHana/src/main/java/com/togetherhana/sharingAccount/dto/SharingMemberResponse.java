package com.togetherhana.sharingAccount.dto;

import com.togetherhana.base.SportsType;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SharingMemberResponse {
    private Long memberIdx;
    private String memberNickName;
    private String imageUrl;
    private Boolean isLeader;
    private Optional<SportsType> sportsType;

    public SharingMemberResponse(Long memberIdx, String memberNickName, String imageUrl, Boolean isLeader, SportsType sportsType) {
        this.memberIdx = memberIdx;
        this.memberNickName = memberNickName;
        this.imageUrl = imageUrl;
        this.isLeader = isLeader;
        this.sportsType = Optional.ofNullable(sportsType);
    }
}
