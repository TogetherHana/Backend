package com.togetherhana.sharingAccount.repository;


import com.togetherhana.sharingAccount.dto.SharingMemberResponse;
import com.togetherhana.sharingAccount.entity.SharingMember;
import java.util.List;

public interface CustomSharingMemberRepository {
    List<SharingMemberResponse> findSharingMemberWithTeam(Long sharingAccountId);
    SharingMember findLeaderOf(Long sharingAccountIdx);
}
