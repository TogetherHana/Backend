package com.togetherhana.transferHistory.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_history_idx")
    private Long id;

    private Long remainBalance;
    private Long transactionAmount;
    private String sender;
    private String recipient;

    @Enumerated(EnumType.STRING)
    private TransactionType transferType;

    @ManyToOne
    @JoinColumn(name = "sharing_account_idx")
    private SharingAccount sharingAccount;
}
