package com.togetherhana.sportClub.repository;

import com.togetherhana.base.SportsType;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyteamRepository extends JpaRepository<MyTeam, Long> {

    MyTeam findByMember_MemberIdxAndSportsClub_Type(Long member_memberIdx, SportsType sportsClub_type);
}
