package com.bunary.vocab.service.specification;

import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.WordSetStat;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class WordSetStatSpec {

    public static Specification<WordSet> buildDynamicFilter(Map<String, String> params) {
        return (root, query, cb) -> {
            Predicate p = cb.conjunction();

            // chỉ fetch user khi query trả về WordSet (không phải count)
            if ("user".equalsIgnoreCase(params.get("include")) && WordSet.class.equals(query.getResultType())) {
                root.fetch("user", JoinType.LEFT);
            }

            // join để sort/filter
            Join<WordSet, WordSetStat> statJoin = root.join("wordSetStat", JoinType.LEFT);

            // filter keyword
            String keyword = params.get("keyword");
            if (keyword != null && !keyword.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%"));
            }

            // filter visibility
            String visibility = params.get("visibility");
            if (visibility != null) {
                p = cb.and(p, cb.equal(root.get("visibility"), visibility.toUpperCase()));
            }

            // sort
            String sort = params.get("sort");
            if (sort != null && !sort.isEmpty()) {
                String sortField = sort.split(",")[0];
                String direction = sort.split(",")[1];

                if (sortField != null && !sortField.isEmpty()) {
                    // chú ý: sort theo WordSetStat chỉ ổn nếu là 1:1
                    if (direction.equalsIgnoreCase("desc")) {
                        query.orderBy(cb.desc(statJoin.get(sortField)));
                    } else {
                        query.orderBy(cb.asc(statJoin.get(sortField)));
                    }
                }
            }

            return p;
        };
    }

}
