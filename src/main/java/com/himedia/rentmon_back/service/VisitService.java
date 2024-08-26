package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Visit;
import com.himedia.rentmon_back.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;

    public List<AdminDTO.ResponseVisit> getAllVisitsGroupedByDate() {
        List<Visit> visits = visitRepository.findAll();

        Map<LocalDate, Long> visitCountByDate = visits.stream()
                .collect(Collectors.groupingBy(
                        visit -> visit.getCreatedAt().toLocalDateTime().toLocalDate(),
                        Collectors.counting()
                ));

        return visitCountByDate.entrySet().stream()
                .map(entry -> AdminDTO.ResponseVisit.builder()
                        .count(entry.getValue().intValue())
                        .createdAt(Timestamp.valueOf(entry.getKey().atStartOfDay()))
                        .build())
                .collect(Collectors.toList());
    }
}
