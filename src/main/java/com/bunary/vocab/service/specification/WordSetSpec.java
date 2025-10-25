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
            // Tránh lỗi count query (Spring Data Page)
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("user", JoinType.LEFT);
            }
            return null;
        };
    }

}
