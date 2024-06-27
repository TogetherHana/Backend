package com.togetherhana.member.dto;

import com.togetherhana.member.entity.DeviceInfo;
import com.togetherhana.member.entity.Member;
import com.togetherhana.mileage.dto.MileageResponse;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.sportClub.dto.MyTeamInfoResponse;
import com.togetherhana.sportClub.entity.MyTeam;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MyInfoResponse {
    private Long memberIdx;

    private String nickname;

    private List<MyTeamInfoResponse> myTeams;

    private MileageResponse mileage;

    public MyInfoResponse(Member member,
                          List<MyTeam> myTeamList) {
        this.memberIdx = member.getMemberIdx();
        this.nickname = member.getNickname();
        this.myTeams = myTeamList.stream().map(MyTeamInfoResponse::new).toList();
        this.mileage = new MileageResponse(member.getMileage());
    }

}
