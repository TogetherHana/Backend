package com.togetherhana.sharingAccount.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.sharingAccount.dto.SharingAccountCreateRequest;
import com.togetherhana.sharingAccount.dto.SharingAccountCreateResponse;
import com.togetherhana.sharingAccount.dto.SharingAccountResponse;
import com.togetherhana.sharingAccount.dto.SharingMemberResponse;
import com.togetherhana.sharingAccount.dto.TaxCollectRequest;
import com.togetherhana.sharingAccount.service.SharingAccountService;
import com.togetherhana.transfer.dto.TransferRequest;
import com.togetherhana.transfer.dto.TransferResponse;
import com.togetherhana.transfer.service.TransferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sharing-account")
public class SharingAccountController {
    private final SharingAccountService sharingAccountService;
    /**
     * memberRepo 의존성은 나중에 삭제
     */
    private final MemberRepository memberRepository;
    private final TransferService transferService;

    @PostMapping("/create")
    public BaseResponse<SharingAccountCreateResponse> createAccount(
            @Auth Member member,
            @RequestBody SharingAccountCreateRequest sharingAccountCreateRequest) {
        return BaseResponse.success(sharingAccountService.createAccount(member, sharingAccountCreateRequest));
    }

    @GetMapping("/my")
    public BaseResponse<List<SharingAccountResponse>> myAccount(@Auth Member member) {
        return BaseResponse.success(sharingAccountService.findMySharingAccounts(member));
    }

    @GetMapping("/members")
    public BaseResponse<List<SharingMemberResponse>> sharingMembers(
            @RequestParam(name = "sharingAccountIdx") Long sharingAccountIdx) {
        return BaseResponse.success(sharingAccountService.findSharingMemberInfo(sharingAccountIdx));
    }

    @GetMapping("/history")
    public BaseResponse<List<TransferResponse>> sharingAccountHistory(
            @RequestParam(name = "sharingAccountIdx") Long sharingAccountIdx) {
        return BaseResponse.success(transferService.findTransferHistory(sharingAccountIdx));
    }

    @PostMapping("/withdraw")
    public BaseResponse<Boolean> withdrawMoney(@Auth Member member, @RequestBody TransferRequest transferRequest) {
        return BaseResponse.success(transferService.withdraw(member, transferRequest));
    }

    @PostMapping("/collect")
    public BaseResponse<Boolean> collectMoney(@Auth Member member, @RequestBody TaxCollectRequest taxCollectRequest) {
        return BaseResponse.success(sharingAccountService.collectMoney(member, taxCollectRequest));
    }

}
