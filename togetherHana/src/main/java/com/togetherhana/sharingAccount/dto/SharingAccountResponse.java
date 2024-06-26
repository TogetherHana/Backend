package com.togetherhana.sharingAccount.dto;

import com.togetherhana.sharingAccount.entity.SharingAccount;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SharingAccountResponse {
    private Long sharingAccountIdx;
    private String accountName;
    private String accountNumber;
    private Long remainBalance;

    public static SharingAccountResponse of(SharingAccount account) {
        return new SharingAccountResponse(account.getSharingAccountIdx(),
                account.getSharingAccountName(),
                String.valueOf(account.getSharingAccountNumber()),
                account.getRemainBalance());
    }

}
