package com.togetherhana.sharingAccount.repository;

import java.util.Optional;

import com.togetherhana.sharingAccount.entity.SharingMember;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SharingMemberRepository extends JpaRepository<SharingMember, Long>, CustomSharingMemberRepository {
  
  Optional<SharingMember> findBySharingAccount_SharingAccountIdxAndMember_MemberIdx(Long sharingAccountIdx, Long memberIdx);
}

