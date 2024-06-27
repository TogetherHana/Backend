package com.togetherhana.mileage.controller;

import com.togetherhana.base.BaseResponse;
import com.togetherhana.mileage.dto.TransferMileageRequest;
import com.togetherhana.mileage.service.MileageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mileage")
public class MileageController {
    private final MileageService mileageService;

    @PutMapping("/transfer")
    public BaseResponse<Boolean> transferMileage(@RequestBody TransferMileageRequest transferMileageRequest) {
        return BaseResponse.success(mileageService.withdrawMileage(transferMileageRequest));
    }

}
