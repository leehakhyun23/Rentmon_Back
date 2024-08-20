package com.himedia.rentmon_back.specification;

import com.himedia.rentmon_back.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
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
}
