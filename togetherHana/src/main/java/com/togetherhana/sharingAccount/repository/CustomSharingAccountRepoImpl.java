package com.togetherhana.sharingAccount.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.entity.SharingAccount;

import static com.togetherhana.sharingAccount.entity.QSharingAccount.sharingAccount;
import static com.togetherhana.sharingAccount.entity.QSharingMember.sharingMember;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomSharingAccountRepoImpl implements CustomSharingAccountRepo {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SharingAccount> findByMember(Member member) {
        return queryFactory.selectFrom(sharingAccount)
                .join(sharingMember)
                .on(sharingAccount.sharingAccountIdx.eq(sharingMember.sharingAccount.sharingAccountIdx))
                .where(sharingMember.member.memberIdx.eq(member.getMemberIdx()))
                .fetch();
    }
}


