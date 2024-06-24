package com.togetherhana.mileage.repository;

import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.sportClub.entity.MyTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MileageRepository extends JpaRepository<Mileage, Long> {

}
