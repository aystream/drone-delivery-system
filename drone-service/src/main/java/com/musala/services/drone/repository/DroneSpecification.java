package com.musala.services.drone.repository;

import com.musala.services.drone.model.entity.Drone;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * For filtering and searching drones
 */
public class DroneSpecification {
    public static Specification<Drone> searchAndFilter(Map<String, String> params) {
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
        // List of searchable fields from Drone
        List<String> searchableFields = Arrays.asList(
                "serialNumber",
                "model",
                "currentWeight",
                "weightLimit",
                "batteryCapacity",
                "state"
        );
        return searchableFields.contains(fieldName);
    }
}