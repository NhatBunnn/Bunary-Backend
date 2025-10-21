package com.bunary.vocab.service.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.enums.VisibilityEnum;

import jakarta.persistence.criteria.JoinType;

public class WordSetSpec {
    public static Specification<WordSet> hasVisibility(VisibilityEnum visibilityEnum) {
        return (root, query, cb) -> {
            if (visibilityEnum == null)
                return cb.conjunction(); // nếu không filter, trả về true
            return cb.equal(root.get("visibility"), visibilityEnum);
        };
    }

    public static Specification<WordSet> fetchUser() {
        return (root, query, cb) -> {
            // Tránh duplicate khi count query (Pageable)
            if (WordSet.class.equals(query.getResultType())) {
                root.fetch("user", JoinType.LEFT);
            }
            return null; // không thêm điều kiện, chỉ fetch join
        };
    }
}
