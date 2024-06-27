package com.vehiculos.unow.infrastructure.adapter.out.db.persistence;

import com.vehiculos.unow.infrastructure.adapter.out.db.model.VehiculoEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehiculoSpecification {
    public static Specification<VehiculoEntity> filterBy(String marca, Short modelo, String matricula) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (marca != null && !marca.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("marca"), marca));
            }

            if (modelo != null) {
                predicates.add(criteriaBuilder.equal(root.get("modelo"), modelo));
            }

            if (matricula != null && !matricula.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("matricula"), matricula));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
