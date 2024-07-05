package com.togetherhana.transfer.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.DeviceInfo;
import com.togetherhana.member.entity.Member;
import com.togetherhana.notification.event.NotificationEvent;
import com.togetherhana.notification.service.NotificationService;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.repository.SharingMemberRepository;
import com.togetherhana.sharingAccount.service.SharingAccountService;
import com.togetherhana.transfer.dto.DepositRequest;
import com.togetherhana.transfer.dto.TransferRequest;
import com.togetherhana.transfer.dto.TransferResponse;
import com.togetherhana.transfer.entity.TransactionType;
import com.togetherhana.transfer.entity.TransferHistory;
import com.togetherhana.transfer.repository.TransferRepository;
import java.util.List;
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
    private final SharingMemberRepository sharingMemberRepository;
    private final NotificationService notificationService;
    private final String DEPOSIT = "입금 금액 : ";
    private final String WITHDRAW = "출금 금액 : ";

    public List<TransferResponse> findTransferHistory(Long sharingAccountIdx) {
        List<TransferHistory> transferHistories = transferRepository.findAllBySharingAccount_SharingAccountIdx(
                sharingAccountIdx);

        return transferHistories.stream().map(transferHistory -> {
            if (transferHistory.getTransferType().equals(TransactionType.DEPOSIT)) {
                return TransferResponse.toDepositResponse(transferHistory);
            }
            return TransferResponse.toWithdrawResponse(transferHistory);
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
        sendNotification(WITHDRAW, account.getSharingAccountIdx(), transferRequest.getAmount());
        return true;
    }

    @Transactional
    public Boolean deposit(DepositRequest depositRequest) {
        SharingAccount account = sharingAccountService.findBySharingAccountIdx(depositRequest.getSharingAccountIdx());
        Long remainBalance = account.deposit(depositRequest.getAmount());
        TransferHistory history = TransferHistory
                .depositHistory(remainBalance,
                        depositRequest.getAmount(),
                        depositRequest.getSender(),
                        account.getSharingAccountName(),
                        account);

        transferRepository.save(history);
        sendNotification(DEPOSIT, account.getSharingAccountIdx(), depositRequest.getAmount());
        return true;
    }

    private void sendNotification(String type, Long sharingAccountIdx, Long amount) {
        List<SharingMember> sharingMembers = sharingMemberRepository.findBySharingAccount_SharingAccountIdx(
                sharingAccountIdx);
        for (SharingMember sharingMember : sharingMembers) {
            List<DeviceInfo> deviceInfos = sharingMember.getMember().getDeviceInfos();
            for (DeviceInfo deviceInfo : deviceInfos) {
                notificationService.publishPushEvent(NotificationEvent.builder()
                        .title("거래 내역 알림")
                        .body(type + String.format("%,d", amount) + " 원")
                        .deviceToken(deviceInfo.getDeviceToken())
                        .build());
            }
        }
    }

    private void verifyPassword(String accountPassword, String inputPassword) {
        if (!accountPassword.equals(inputPassword)) {
            throw new BaseException(ErrorType.WRONG_PASSWORD);
        }
    }
}
