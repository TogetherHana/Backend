package com.togetherhana.sharingAccount.repository;

import java.util.Optional;

import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.entity.SharingAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SharingMemberRepository extends JpaRepository<SharingMember, Long>, CustomSharingMemberRepository {
  
  Optional<SharingMember> findBySharingAccountAndMember_MemberIdx(SharingAccount sharingAccount, Long memberIdx);
}

