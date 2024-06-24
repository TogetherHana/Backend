package com.togetherhana.member.repository;

import com.togetherhana.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByPhoneNumberAndName(String phoneNumber, String name);
}
