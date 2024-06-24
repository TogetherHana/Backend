package com.togetherhana.sharingAccount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;

public interface SharingMemberRepository extends JpaRepository<SharingMember, Long> {

	Optional<SharingMember> findBySharingAccountAndMember_MemberIdx(SharingAccount sharingAccount, Long memberIdx);
}
