package com.togetherhana.mileage.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.mileage.dto.TransferMileageRequest;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.mileage.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageService {
    private final MileageRepository mileageRepository;

    @Transactional
    public Boolean withdrawMileage(TransferMileageRequest transferMileageRequest) {
        // 원래 마일리지 정보 조회
        Mileage mileage = mileageRepository.findById(transferMileageRequest.getMileageIdx())
                        .orElseThrow(() -> new BaseException(ErrorType.INVAILD_MILEAGE_IDX));

        // 마일리지 사용(잔액 -)
        mileage.withdraw(transferMileageRequest.getWithdrawalAmount());

        return true;
    }

    @Transactional
    public Boolean depositMileage(Long memberIdx, Long depositAmount) {
        log.info("마일리지 지급 : {}", memberIdx);
        // 마일리지 정보 조회
        Mileage mileage = mileageRepository.findMileageByMemberIdx(memberIdx);

        // 마일리지가 없는 경우 예외 처리
        if(mileage == null){
            log.info("마일리지 정보가 없습니다. : {}", memberIdx);
            throw new BaseException(ErrorType.INVAILD_MILEAGE_IDX);
        }

        // 마일리지 입금(잔액 +)
        mileage.deposit(depositAmount);

        return true;
    }


}
