package com.togetherhana.member.repository;

import com.togetherhana.member.entity.Member;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT distinct m FROM member m JOIN m.deviceInfos d WHERE d.deviceToken = :deviceToken")
    Member findByDeviceToken(@Param("deviceToken") String deviceToken);

    Member findByNickname(String nickname);

    Member findByPhoneNumberAndName(String phoneNumber, String name);
    Member findByMemberIdx(Long memberIdx);

}
