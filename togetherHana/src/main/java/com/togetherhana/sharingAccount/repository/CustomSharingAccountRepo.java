package com.togetherhana.sharingAccount.repository;

import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import java.util.List;

public interface CustomSharingAccountRepo {
    List<SharingAccount> findByMember(Member member);
}
