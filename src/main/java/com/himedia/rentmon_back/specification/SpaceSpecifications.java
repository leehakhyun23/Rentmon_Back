package com.himedia.rentmon_back.specification;

import com.himedia.rentmon_back.entity.HashSpace;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class SpaceSpecifications {

    public static Specification<Space> hasSearchword(String searchword) {
        return (root, query, criteriaBuilder) -> {
            Join<Space, HashSpace> hashJoin = root.join("hashtags");
            return criteriaBuilder.like(hashJoin.get("hseq").get("word"), "%" + searchword + "%");
        };
    }

    public static Specification<Space> hasProvince(String province) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("province"), province);
    }

    public static Specification<Space> isAvailableDuring(String reservestart, String reserveend) {
        return (root, query, criteriaBuilder) -> {
            // Space, Reservation 조인
            Join<Space, Reservation> reservationJoin = root.join("reservations", JoinType.LEFT);

            // 문자열 -> 타임스탬프 변환
            Timestamp startTimestamp = Timestamp.valueOf(reservestart);
            Timestamp endTimestamp = Timestamp.valueOf(reserveend);

            // Create the predicates
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(reservationJoin.get("reserveend"), startTimestamp);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(reservationJoin.get("reservestart"), endTimestamp);
            Predicate overlapPredicate = criteriaBuilder.and(startPredicate, endPredicate);

            // Find spaces where there is no reservation overlapping
            Predicate noOverlapPredicate = criteriaBuilder.not(overlapPredicate);

            // Apply the predicate to the query
            query.where(noOverlapPredicate);

            // Group by Space to ensure no duplicates
            query.groupBy(root.get("sseq"));

            return query.getRestriction();
        };
    }
}