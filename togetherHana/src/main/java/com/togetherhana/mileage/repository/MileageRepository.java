package com.togetherhana.mileage.repository;

import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.sportClub.entity.MyTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MileageRepository extends JpaRepository<Mileage, Long> {
    @Query("SELECT m.mileage FROM member m WHERE m.memberIdx = :memberIdx")
    Mileage findMileageByMemberIdx(@Param("memberIdx") Long memberIdx);
}
