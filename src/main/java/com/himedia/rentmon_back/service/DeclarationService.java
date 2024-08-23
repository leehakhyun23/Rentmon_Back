package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.repository.DeclarationRepository;
import com.himedia.rentmon_back.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeclarationService {

    private final DeclarationRepository declarationRepository;

    public void insertDeclaration(Declaration declaration) {
        declarationRepository.save(declaration);
    }
}
