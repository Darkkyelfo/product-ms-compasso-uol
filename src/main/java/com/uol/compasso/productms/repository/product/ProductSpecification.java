package com.uol.compasso.productms.repository.product;

import com.uol.compasso.productms.model.entity.Product;
import com.uol.compasso.productms.repository.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria criteria;

    public ProductSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Product> productRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (this.criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(
                    productRoot.get(this.criteria.getKey()), this.criteria.getValue().toString());
        } else if (this.criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(
                    productRoot.get(this.criteria.getKey()), this.criteria.getValue().toString());
        } else if (this.criteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(
                    productRoot.get(this.criteria.getKey()), this.criteria.getValue().toString());
        } else if (this.criteria.getOperation().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(
                    productRoot.get(this.criteria.getKey()), this.criteria.getValue().toString());
        } else if (this.criteria.getOperation().equalsIgnoreCase("like")) {
            return builder.like(builder.lower(productRoot.get(this.criteria.getKey())), "%" + this.criteria.getValue().toString().toLowerCase() + "%");
        }
        return null;
    }
}
