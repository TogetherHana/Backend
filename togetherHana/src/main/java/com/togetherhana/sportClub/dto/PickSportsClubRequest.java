package com.togetherhana.sportClub.dto;

import com.togetherhana.member.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PickSportsClubRequest {
    private Long sportsClubIdx;
    private Member member;
}
