package com.togetherhana.mileage.service;

import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.mileage.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MileageService {
    private final MileageRepository mileageRepository;

}
