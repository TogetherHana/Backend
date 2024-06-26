package com.togetherhana.transfer.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.service.SharingAccountService;
import com.togetherhana.transfer.dto.TransferRequest;
import com.togetherhana.transfer.dto.TransferResponse;
import com.togetherhana.transfer.dto.TransferResponse.DepositResponse;
import com.togetherhana.transfer.dto.TransferResponse.WithdrawalResponse;
import com.togetherhana.transfer.entity.TransactionType;
import com.togetherhana.transfer.entity.TransferHistory;
import com.togetherhana.transfer.repository.TransferRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {
    private final TransferRepository transferRepository;
    private final SharingAccountService sharingAccountService;

    public List<TransferResponse> findTransferHistory(Long sharingAccountIdx) {
        List<TransferHistory> transferHistories = transferRepository.findAllBySharingAccount_SharingAccountIdx(
                sharingAccountIdx);

        return transferHistories.stream().map(transferHistory -> {
            if (transferHistory.getTransferType().equals(TransactionType.DEPOSIT)) {
                return DepositResponse.of(transferHistory);
            }
            return WithdrawalResponse.of(transferHistory);
        }).toList();
    }

    @Transactional
    public Boolean withdraw(Member member, TransferRequest transferRequest) {

        sharingAccountService.verifyIsLeader(transferRequest.getSharingAccountIdx(), member.getMemberIdx());

        SharingAccount account = sharingAccountService.findBySharingAccountIdx(transferRequest.getSharingAccountIdx());
        verifyPassword(account.getSharingAccountPassword(), transferRequest.getAccountPassword());

        Long remainBalance = account.withdraw(transferRequest.getAmount());
        TransferHistory history = TransferHistory
                .withdrawHistory(remainBalance,
                        transferRequest.getAmount(),
                        member.getName(),
                        transferRequest.getReceiver(),
                        account);

        transferRepository.save(history);
        return true;
    }

    private void verifyPassword(String accountPassword, String inputPassword) {
        if (!accountPassword.equals(inputPassword)) {
            throw new BaseException(ErrorType.WRONG_PASSWORD);
        }
    }
}
