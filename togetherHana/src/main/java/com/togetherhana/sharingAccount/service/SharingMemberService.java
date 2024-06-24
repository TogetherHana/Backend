package com.togetherhana.sharingAccount.service;

import static com.togetherhana.exception.ErrorType.*;

import org.springframework.stereotype.Service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.repository.SharingMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharingMemberService {

	private final SharingMemberRepository sharingMemberRepository;

	public SharingMember findBySharingAccountAndMemberIdx(SharingAccount sharingAccount, Long memberIdx) {
		return sharingMemberRepository.findBySharingAccountAndMember_MemberIdx(sharingAccount, memberIdx)
			.orElseThrow(() -> new BaseException(SHARING_MEMBER_NOT_FOUND));
	}
}
