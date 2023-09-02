package com.musala.services.medication.repository;

import com.musala.services.medication.model.entity.Medication;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * For filtering and searching medications
 */
public class MedicationSpecification {
    public static Specification<Medication> searchAndFilter(Map<String, String> params) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : params.keySet()) {
                if (isFieldSearchable(key)) {
                    if (root.get(key) != null && !params.get(key).isEmpty()) {
                        predicates.add(cb.like(root.get(key).as(String.class), "%" + params.get(key) + "%"));
                    }
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean isFieldSearchable(String fieldName) {
        // List of searchable fields from Medication entity
        List<String> searchableFields = Arrays.asList(
                "code",
                "name",
                "weight",
                "imageUrl",
                "droneSerialNumber"
        );

        return searchableFields.contains(fieldName);
    }
}