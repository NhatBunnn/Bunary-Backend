package com.bunary.vocab.service.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.bunary.vocab.model.Role;
import com.bunary.vocab.model.User;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class UserSpec {

    public static Specification<User> keyword(String keyword) {
        return (root, query, cb) -> {

            if (keyword == null || keyword.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<User> equalTo(String field, String value) {
        return (root, query, cb) -> {

            if (value == null || value.isEmpty()) {
                return cb.conjunction();
            }

            return cb.equal(root.get(field), value);
        };
    }

    public static Specification<User> hasRoles(String roleNames) {
        return (root, query, cb) -> {
            List<String> roleList = roleNames != null ? Arrays.asList(roleNames.split(",")) : null;

            if (roleList == null || roleList.isEmpty()) {
                return cb.conjunction();
            }

            Join<User, Role> rolesJoin = root.join("roles", JoinType.INNER);

            query.distinct(true);

            return rolesJoin.get("name").in(roleList);
        };
    }

    public static Specification<User> createdAtFilter(Map<String, String> filters) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (filters == null || filters.isEmpty())
                return predicate;

            String gte = filters.get("createdAt_gte");
            String lte = filters.get("createdAt_lte");

            try {
                if (gte != null && !gte.isEmpty()) {
                    LocalDate dateGte = LocalDate.parse(gte.trim());
                    LocalDateTime startOfDay = dateGte.atStartOfDay();
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), startOfDay));
                }

                if (lte != null && !lte.isEmpty()) {
                    LocalDate dateLte = LocalDate.parse(lte.trim());
                    LocalDateTime endOfDay = dateLte.atTime(23, 59, 59);
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), endOfDay));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return predicate;
        };
    }

}
