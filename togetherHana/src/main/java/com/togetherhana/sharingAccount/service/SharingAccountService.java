package com.togetherhana.sharingAccount.service;

import static com.togetherhana.exception.ErrorType.*;

import org.springframework.stereotype.Service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.repository.SharingAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SharingAccountService {

	private final SharingAccountRepository sharingAccountRepository;

	public SharingAccount findBySharingAccountIdx(Long sharingAccountIdx) {
		return sharingAccountRepository.findById(sharingAccountIdx)
			.orElseThrow(() -> new BaseException(SHARING_ACCOUNT_NOT_FOUND));
	}

}
