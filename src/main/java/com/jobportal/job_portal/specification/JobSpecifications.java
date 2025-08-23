package com.jobportal.job_portal.specification;

import org.springframework.data.jpa.domain.Specification;
import com.jobportal.job_portal.models.Job;

public class JobSpecifications {

    public static Specification<Job> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return null;
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), likePattern),
                    cb.like(cb.lower(root.get("description")), likePattern)
            );
        };
    }

    public static Specification<Job> hasLocation(String location) {
        return (root, query, cb) ->
                (location == null || location.isEmpty()) ? null :
                        cb.equal(cb.lower(root.get("location")), location.toLowerCase());
    }

    public static Specification<Job> hasType(String type) {
        return (root, query, cb) ->
                (type == null || type.isEmpty()) ? null :
                        cb.equal(cb.lower(root.get("type")), type.toLowerCase());
    }

    public static Specification<Job> hasMinSalary(Double minSalary) {
        return (root, query, cb) ->
                (minSalary == null) ? null : cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
    }

    public static Specification<Job> hasMaxSalary(Double maxSalary) {
        return (root, query, cb) ->
                (maxSalary == null) ? null : cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
    }
}
