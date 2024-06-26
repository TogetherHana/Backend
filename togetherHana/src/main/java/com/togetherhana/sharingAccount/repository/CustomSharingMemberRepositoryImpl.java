package com.togetherhana.sharingAccount.repository;

import static com.togetherhana.member.entity.QMember.member;
import static com.togetherhana.sharingAccount.entity.QSharingMember.sharingMember;
import static com.togetherhana.sportClub.entity.QMyTeam.myTeam;
import static com.togetherhana.sportClub.entity.QSportsClub.sportsClub;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togetherhana.sharingAccount.dto.SharingMemberResponse;
import com.togetherhana.sharingAccount.entity.SharingMember;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomSharingMemberRepositoryImpl implements CustomSharingMemberRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SharingMemberResponse> findSharingMemberWithTeam(Long sharingAccountIdx) {
        return jpaQueryFactory.select(Projections.constructor(SharingMemberResponse.class,
                        member.memberIdx,
                        member.nickname,
                        sportsClub.imgUrl,
                        sharingMember.isLeader
                ))
                .from(member)
                .join(myTeam)
                .on(myTeam.member.memberIdx.eq(member.memberIdx))
                .join(sportsClub)
                .on(sportsClub.sportsClubIdx.eq(myTeam.sportsClub.sportsClubIdx))
                .join(sharingMember)
                .on(sharingMember.member.memberIdx.eq(member.memberIdx))
                .where(sharingMember.sharingAccount.sharingAccountIdx.eq(sharingAccountIdx))
                .fetch();
    }

    @Override
    public SharingMember findLeaderOf(Long sharingAccountIdx) {
        return jpaQueryFactory.selectFrom(sharingMember)
                .where(sharingMember.sharingAccount.sharingAccountIdx.eq(sharingAccountIdx)
                        .and(sharingMember.isLeader.isTrue()))
                .fetchOne();
    }
}
