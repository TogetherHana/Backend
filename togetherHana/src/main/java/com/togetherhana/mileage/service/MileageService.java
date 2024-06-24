package com.togetherhana.mileage.service;

import com.togetherhana.mileage.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageService {
    private final MileageRepository mileageRepository;

}
