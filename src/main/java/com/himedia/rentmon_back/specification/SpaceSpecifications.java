package com.himedia.rentmon_back.specification;

import com.himedia.rentmon_back.entity.HashSpace;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;

public class SpaceSpecifications {

    public static Specification<Space> hasCnum(int cnum) {
        return (root, query, builder) -> builder.equal(root.get("category").get("cnum"), cnum);
    }

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
            // Subquery to find spaces that are reserved during the given time
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Reservation> reservationRoot = subquery.from(Reservation.class);
            subquery.select(reservationRoot.get("space").get("sseq"));

            Timestamp startTimestamp = Timestamp.valueOf(reservestart);
            Timestamp endTimestamp = Timestamp.valueOf(reserveend);

            // Find reservations that do NOT overlap
            Predicate noOverlapStartPredicate = criteriaBuilder.greaterThanOrEqualTo(reservationRoot.get("reservestart"), endTimestamp);
            Predicate noOverlapEndPredicate = criteriaBuilder.lessThanOrEqualTo(reservationRoot.get("reserveend"), startTimestamp);
            Predicate noOverlapPredicate = criteriaBuilder.or(noOverlapStartPredicate, noOverlapEndPredicate);

            // Select spaces where there IS an overlap
            subquery.where(criteriaBuilder.not(noOverlapPredicate));

            // Main query: Find spaces not in the subquery result (i.e., spaces with no overlapping reservations)
            return criteriaBuilder.not(root.get("sseq").in(subquery));
        };
    }
}