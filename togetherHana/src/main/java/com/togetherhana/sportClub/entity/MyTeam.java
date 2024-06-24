package com.togetherhana.sportClub.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="myteam")
@Table(name="myteam")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class MyTeam extends BaseEntity {

    // 응원팀 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "my_team_idx")
    private Long myTeamIdx;

    // 멤버 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    // 구단 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_club_idx")
    private SportsClub sportsClub;

}
