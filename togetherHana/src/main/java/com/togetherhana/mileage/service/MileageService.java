package com.togetherhana.mileage.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.mileage.dto.TransferMileageRequest;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.mileage.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MileageService {
    private final MileageRepository mileageRepository;

    @Transactional
    public Boolean withdrawMileage(TransferMileageRequest transferMileageRequest) {
        // 원래 마일리지 정보 조회
        Mileage mileage = mileageRepository.findById(transferMileageRequest.getMileageIdx())
                        .orElseThrow(() -> new BaseException(ErrorType.INVAILD_MILEAGE_IDX));

        // 마일리지 사용(잔액 -)
        long usedAmount = mileage.withdraw(transferMileageRequest.getWithdrawalAmount());

        return true;
    }

}
