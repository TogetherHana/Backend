package com.togetherhana.sharingAccount.repository;

import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.entity.SharingMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharingMemberRepository extends JpaRepository<SharingMember, Long>, CustomSharingMemberRepository {

}
