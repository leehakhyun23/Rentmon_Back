package com.himedia.rentmon_back.specification;

import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecification {
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
    }

    public static class HostSpe {
        public static Specification<Host> searchByHostList(String searchType, String keyword) {
            return (Root<Host> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                if (searchType == null || keyword == null) {
                    return criteriaBuilder.conjunction();
                }

                return switch (searchType) {
                    case "nickname" -> criteriaBuilder.like(root.get("nickname"), "%" + keyword + "%");
                    case "phone" -> criteriaBuilder.like(root.get("phone"), "%" + keyword + "%");
                    case "email" -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                    default -> criteriaBuilder.conjunction();
                };
            };
        }
    }

    public static class DeclarationSpe {
        public static Specification<Declaration> searchByDeclarationList(String searchType, String keyword) {
            return (Root<Declaration> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                if (searchType == null || keyword == null) {
                    return criteriaBuilder.conjunction();
                }

                return switch (searchType) {
                    case "a" -> criteriaBuilder.like(root.get("a"), "%" + keyword + "%");
                    case "phone" -> criteriaBuilder.like(root.get("phone"), "%" + keyword + "%");
                    case "email" -> criteriaBuilder.like(root.get("email"), "%" + keyword + "%");
                    default -> criteriaBuilder.conjunction();
                };
            };
        }
    }
}
