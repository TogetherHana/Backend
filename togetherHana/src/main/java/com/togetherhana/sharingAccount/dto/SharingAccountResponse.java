package com.togetherhana.sharingAccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SharingAccountResponse {
    private Long sharingAccountIdx;
    private String accountName;
    @JsonInclude(Include.NON_NULL)
    private String accountNumber;
    @JsonInclude(Include.NON_NULL)
    private Long remainBalance;

    public static SharingAccountResponse of(SharingAccount account) {
        return new SharingAccountResponse(account.getSharingAccountIdx(),
                account.getSharingAccountName(),
                String.valueOf(account.getSharingAccountNumber()),
                account.getRemainBalance());
    }

    public static SharingAccountResponse invitation(SharingAccount account) {
        return new SharingAccountResponse(account.getSharingAccountIdx(),
                account.getSharingAccountName(),
                null,
                null);
    }

}
