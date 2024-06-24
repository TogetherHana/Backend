package com.togetherhana.sportClub.repository;

import com.togetherhana.base.SportsType;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportsClubRepository extends JpaRepository<SportsClub, Long> {
    List<SportsClub> findByType(SportsType type);
}
