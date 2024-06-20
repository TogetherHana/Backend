package com.togetherhana.sharingAccount.entity;

import com.togetherhana.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="sharing_account")
@Table(name="sharing_account")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SharingAccount extends BaseEntity {

    // 모임통장 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sharing_account_idx")
    private Long sharingAccountIdx;

    // 모임통장명
    private String sharingAccountName;

    // 계좌 잔액
    private Long remainBalance;

    // 계좌번호
    private Long sharingAccountNumber;

    private String sharePurpose;

    private String sharingAccountPassword;

}
