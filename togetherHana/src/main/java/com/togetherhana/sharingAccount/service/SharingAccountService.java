package com.togetherhana.sharingAccount.service;

import static com.togetherhana.exception.ErrorType.LEADER_PRIVILEGES_REQUIRED;
import static com.togetherhana.exception.ErrorType.SHARING_MEMBER_NOT_FOUND;

import com.togetherhana.base.SportsType;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.dto.SharingAccountCreateRequest;
import com.togetherhana.sharingAccount.dto.SharingAccountCreateResponse;
import com.togetherhana.sharingAccount.dto.SharingAccountResponse;
import com.togetherhana.sharingAccount.dto.SharingMemberResponse;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.repository.SharingAccountRepository;
import com.togetherhana.sharingAccount.repository.SharingMemberRepository;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SharingAccountService {
    private final SharingAccountRepository sharingAccountRepository;
    private final SharingMemberRepository sharingMemberRepository;

    @Transactional
    public SharingAccountCreateResponse createAccount(Member member, SharingAccountCreateRequest createRequest) {

        try {
            SharingAccount sharingAccount = SharingAccount.builder()
                    .sharingAccountName(createRequest.getAccountName())
                    .sharingAccountPassword(createRequest.getAccountPassword())
                    .sharePurpose(SportsType.byName(createRequest.getSharePurpose()))
                    .remainBalance(0L)
                    .sharingAccountNumber(createAccountNumber()).build();

            SharingAccount save = sharingAccountRepository.save(sharingAccount);

            SharingMember sharingMember = SharingMember.builder()
                    .member(member)
                    .sharingAccount(save)
                    .isLeader(Boolean.TRUE).build();
            sharingMemberRepository.save(sharingMember);
            return new SharingAccountCreateResponse(save.getSharingAccountNumber().toString(),
                    save.getSharingAccountName(), member.getName(), save.getCreatedAt());
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(ErrorType.BAD_REQUEST);
        }
    }

    public SharingAccount findBySharingAccountIdx(Long sharingAccountIdx) {
        return sharingAccountRepository.findById(sharingAccountIdx).orElseThrow(
                () -> new BaseException(ErrorType.SHARING_ACCOUNT_NOT_FOUND)
        );
    }

    public SharingMember findBySharingAccountAndMemberIdx(Long sharingAccountIdx, Long memberIdx) {
        return sharingMemberRepository.findBySharingAccount_SharingAccountIdxAndMember_MemberIdx(sharingAccountIdx, memberIdx)
                .orElseThrow(() -> new BaseException(SHARING_MEMBER_NOT_FOUND));
    }

    public List<SharingAccountResponse> findMySharingAccounts(Member member) {
        return sharingAccountRepository.findByMember(member).stream().map(SharingAccountResponse::of
        ).toList();
    }

    public List<SharingMemberResponse> findSharingMemberInfo(Long sharingAccountIdx) {
        return sharingMemberRepository.findSharingMemberWithTeam(sharingAccountIdx);
    }

    public void verifyIsLeader(Long sharingAccountIdx, Long memberIdx) {
        SharingMember sharingMember = sharingMemberRepository.findBySharingAccount_SharingAccountIdxAndMember_MemberIdx(sharingAccountIdx,
                        memberIdx)
                .orElseThrow(() -> new BaseException(SHARING_MEMBER_NOT_FOUND));

        if (sharingMember.getIsLeader().equals(Boolean.FALSE)) {
            throw new BaseException(LEADER_PRIVILEGES_REQUIRED);
        }
    }


    private Long createAccountNumber() {
        Random random = new Random();
        long LOW_BOUND = 10_000_000_000L;
        long MAX_BOUND = 90_000_000_000L;
        return random.nextLong(MAX_BOUND) + LOW_BOUND;
    }
}
