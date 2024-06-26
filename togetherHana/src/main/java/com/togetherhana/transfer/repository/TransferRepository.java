package com.togetherhana.transfer.repository;

import com.togetherhana.transfer.entity.TransferHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferHistory, Long> {
    List<TransferHistory> findAllBySharingAccount_SharingAccountIdx(Long sharingAccountIdx);
}
