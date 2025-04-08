package com.es.API_REST_Ez_Learning.specifications;

import com.es.API_REST_Ez_Learning.model.Test;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TestSpecification {
    public static Specification<Test> filtrarTests(String titulo, String dificultad, String tipo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("titulo"), "%" + titulo + "%"));
            }

            if (dificultad != null) {
                predicates.add(criteriaBuilder.equal(root.get("dificultad"), dificultad));
            }

            if (tipo != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo.toUpperCase()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}