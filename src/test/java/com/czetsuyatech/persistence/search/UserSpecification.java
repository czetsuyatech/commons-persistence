package com.czetsuyatech.persistence.search;

import com.czetsuyatech.persistence.entity.UserEntity;
import com.czetsuyatech.persistence.search.constant.RelationalOperators;
import com.czetsuyatech.persistence.search.constant.SpecificationConstant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class UserSpecification implements Specification<UserEntity> {

  private transient SearchCriteria criteria;

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

    var key = UserSearchFields.valueOf(criteria.getKey().toUpperCase());
    RelationalOperators operator = RelationalOperators.getOperator(criteria.getOperation());
    Object value = criteria.getValue();
    Expression<?> expression = root.<String>get(key.toString());

    Predicate predicate = null;
    switch (operator) {
      case EQUAL -> predicate = criteriaBuilder.equal(expression, value);
      case LIKE -> predicate = criteriaBuilder.like(criteriaBuilder.upper((Expression<String>) expression),
          SpecificationConstant.LIKE_WILDCARD + value.toString().toUpperCase() + SpecificationConstant.LIKE_WILDCARD);
    }

    return predicate;
  }
}
