package com.czetsuyatech.persistence.search;

import com.czetsuyatech.persistence.search.constant.RelationalOperators;
import com.czetsuyatech.persistence.search.constant.SpecificationConstant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.util.Collection;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = -2769484968147454259L;

  private SearchCriteria criteria;

  public static String IN = "IN";
  public static String JOIN = "JOIN";
  public static String NOT_NULL = "NOT_NULL";

  public GenericSpecification(final SearchCriteria searchCriteria) {
    this.criteria = searchCriteria;
  }

  @Override
  public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

    String operation = criteria.getOperation();
    String key = criteria.getKey();
    Object value = criteria.getValue();

    if (criteria.getOperation().equalsIgnoreCase(RelationalOperators.GREATER_THAN_EQUAL.toString())) {
      return buildComparison(root, builder,
          (expr, val) -> {
            return builder.greaterThanOrEqualTo(expr, val);
          });
    }

    if (criteria.getOperation().equalsIgnoreCase(RelationalOperators.LESS_THAN_EQUAL.toString())) {
      return buildComparison(root, builder,
          (expr, val) -> {
            return builder.lessThanOrEqualTo(expr, val);
          });
    }

    if (RelationalOperators.EQUAL.toString().equalsIgnoreCase(operation)) {
      return builder.equal(root.get(key), value);
    }

    if (RelationalOperators.NOTEQUAL.toString().equalsIgnoreCase(operation)) {
      return builder.notEqual(root.get(key), value);
    }

    if (RelationalOperators.LIKE.toString().equalsIgnoreCase(operation)
        && root.get(key).getJavaType() == String.class) {
      return builder.like(root.get(key),
          SpecificationConstant.LIKE_WILDCARD + value + SpecificationConstant.LIKE_WILDCARD);
    }

    if (RelationalOperators.IN.toString().equalsIgnoreCase(operation) && value instanceof Collection) {
      return root.get(key).in(value);
    }

    if (RelationalOperators.JOIN.toString().equalsIgnoreCase(operation)) {
      String[] split = key.split("\\.");
      return builder.equal(root.join(split[0]).get(split[1]), value);
    }

    if (RelationalOperators.NOTNULL.toString().equals(operation)) {
      return root.get(key).isNotNull();
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  private <Y extends Comparable<? super Y>> Predicate buildComparison(
      Root<T> root,
      CriteriaBuilder builder,
      ComparisonBuilder<Y> comparisonBuilder) {

    if (root.get(criteria.getKey()).getJavaType() == Instant.class) {
      return comparisonBuilder.build(root.get(criteria.getKey()), (Y) criteria.getValue());

    } else {
      return comparisonBuilder.build(root.get(criteria.getKey()), (Y) criteria.getValue().toString());
    }
  }

  @FunctionalInterface
  private interface ComparisonBuilder<Y extends Comparable<? super Y>> {

    Predicate build(Expression<? extends Y> expression, Y value);
  }
}
