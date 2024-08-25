package com.himedia.rentmon_back.specification;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AdminSpecification {
    public static class AdminReservationSpe {
        public static Specification<Reservation> withinPeriod(String period) {
            return (Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime startDate = switch (period.toLowerCase()) {
                    case "monthly" -> now.minusMonths(12);
                    case "weekly" -> now.minusWeeks(15);
                    case "daily" -> now.minusDays(30);
                    default -> throw new IllegalArgumentException("Invalid period: " + period);
                };

                return criteriaBuilder.between(
                        root.get("reservestart"),
                        Timestamp.valueOf(startDate),
                        Timestamp.valueOf(now)
                );
            };
        }
    }

    public static class UserSpe {
        public static Specification<User> searchByUserList(String searchType, String keyword) {
            return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                if (searchType == null || keyword == null) {
                    return criteriaBuilder.conjunction();
                }

                return switch (searchType) {
                    case "name" -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
                    case "phone" -> criteriaBuilder.like(root.get("phone"), "%" + keyword + "%");
                    case "email" -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                    default -> criteriaBuilder.conjunction();
                };
            };
        }

        public static Specification<User> filterByLoginStatus(Boolean isLogin) {
            return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("islogin"), isLogin);
        }
    }

    public static class HostSpe {
        public static Specification<Host> searchByHostList(String searchType, String keyword) {
            return (root, query, criteriaBuilder) -> {
                if (searchType == null || keyword == null) {
                    return criteriaBuilder.conjunction();
                }

                return switch (searchType) {
                    case "hostid" -> criteriaBuilder.like(root.get("hostid"), "%" + keyword + "%");
                    case "nickname" -> criteriaBuilder.like(root.get("nickname"), "%" + keyword + "%");
                    case "phone" -> criteriaBuilder.like(root.get("phone"), "%" + keyword + "%");
                    case "email" -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                    case "title" -> {
                        Join<Host, Space> titleJoin = root.join("spaces", JoinType.INNER);
                        yield criteriaBuilder.like(titleJoin.get("title"), "%" + keyword + "%");
                    }
                    case "category" -> {
                        Join<Host, Space> categoryJoin = root.join("spaces", JoinType.INNER);
                        yield criteriaBuilder.like(categoryJoin.get("category").get("name"), "%" + keyword + "%");
                    }
                    default -> criteriaBuilder.conjunction();
                };
            };
        }
    }
}
