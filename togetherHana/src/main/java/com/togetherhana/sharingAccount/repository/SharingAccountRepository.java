package com.togetherhana.sharingAccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.sharingAccount.entity.SharingAccount;

public interface SharingAccountRepository extends JpaRepository<SharingAccount, Long>, CustomSharingAccountRepo{
}
