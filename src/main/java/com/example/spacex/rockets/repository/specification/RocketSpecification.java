package com.example.spacex.rockets.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.spacex.rockets.model.Length;
import com.example.spacex.rockets.model.Rocket;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class RocketSpecification {

    public static Specification<Rocket> between(String column, Comparable lowerBound, Comparable upperBound) {
        return (root, query, criteriaBuilder) -> {
            if (lowerBound != null && upperBound != null) {
                return criteriaBuilder.between(root.get(column), lowerBound, upperBound);
            } else if (lowerBound != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(column), lowerBound);
            } else if (upperBound != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(column), upperBound);
            }
            return null;
        };
    }

    public static Specification<Rocket> equalTo(String column, Comparable value) {
        return (root, query, criteriaBuilder) -> {
            if (value != null) {
                return criteriaBuilder.equal(root.get(column), value);
            }
            return null;
        };
    }

    public static Specification<Rocket> equalToJoinedTableField(String table, String column, Comparable value) {
        return (root, query, criteriaBuilder) -> {
            Join<Rocket, Length> lengthJoin = root.join(table, JoinType.INNER);

            if (value != null) {
                return criteriaBuilder.equal(lengthJoin.get(column), value);
            }

            return null;
        };
    }
}
