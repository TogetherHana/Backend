package com.togetherhana.member.repository;

import com.togetherhana.member.entity.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, Long> {
}
