package com.czetsuyatech.persistence.persistence.repositories;

import com.czetsuyatech.persistence.persistence.search.QueryProjectionUtils;
import com.czetsuyatech.persistence.persistence.search.SimpleSliceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;

public class SimpleSliceJpaRepositoryImpl<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements
    SliceJpaRepository<T, I> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSliceJpaRepositoryImpl.class);
  private final EntityManager entityManager;

  public SimpleSliceJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {

    super(entityInformation, entityManager);
    Assert.notNull(entityManager, "EntityManager must not be null!");
    this.entityManager = entityManager;
  }

  public SimpleSliceJpaRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {

    super(domainClass, entityManager);
    this.entityManager = entityManager;
  }

  protected <S extends T> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable,
      Specification<S> spec) {

    if (pageable.isPaged()) {
      query.setFirstResult((int) pageable.getOffset());
      int pageSize = pageable.getPageSize();
      if (pageable.getPageSize() < Integer.MAX_VALUE) {
        pageSize++;
      }
      query.setMaxResults(pageSize);
    }

    List<S> content = query.getResultList();
    int totalKnownElements = (int) pageable.getOffset() + content.size();
    if (content.size() > pageable.getPageSize()) {
      content.remove(content.size() - 1);
    }

    return PageableExecutionUtils.getPage(content, pageable, () -> (long) totalKnownElements);
  }

  public Slice<T> findAllSlice(Specification<T> spec, Pageable pageable) {

    Page<T> result = this.findAll(spec, pageable);
    return new SimpleSliceImpl(result.getContent(), pageable, result.hasNext());
  }

  public <S> S findOne(Specification<T> spec, Class<S> projectionType) {

    CriteriaQuery<Tuple> criteriaQuery = this.createQuery(spec, (Sort) null, projectionType);
    TypedQuery<Tuple> query = this.entityManager.createQuery(criteriaQuery);
    query.setMaxResults(1);

    try {
      Tuple tuple = (Tuple) query.getSingleResult();
      return (S) QueryProjectionUtils.createProjection(tuple, projectionType);

    } catch (NoResultException e) {
      LOGGER.warn(e.getMessage(), e);
      return null;
    }
  }

  public <S> List<S> findAll(Specification<T> spec, Class<S> projectionType) {

    CriteriaQuery<Tuple> criteriaQuery = this.createQuery(spec, (Sort) null, projectionType);
    TypedQuery<Tuple> query = this.entityManager.createQuery(criteriaQuery);
    List<Tuple> results = query.getResultList();

    return QueryProjectionUtils.createProjection(results, projectionType);
  }

  public <S> Page<S> findAll(Specification<T> spec, Pageable pageable, Class<S> projectionType) {

    CriteriaQuery<Tuple> criteriaQuery = this.createQuery(spec, pageable.getSort(), projectionType);
    TypedQuery<Tuple> query = this.entityManager.createQuery(criteriaQuery);
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());
    List<Tuple> results = query.getResultList();
    List<S> projectedResults = QueryProjectionUtils.createProjection(results, projectionType);

    return this.readPage(projectedResults, this.getEntityType(), pageable, spec);
  }

  public <S> List<S> findAll(Specification<T> spec, Sort sort, Class<S> projectionType) {

    CriteriaQuery<Tuple> criteriaQuery = this.createQuery(spec, sort, projectionType);
    TypedQuery<Tuple> query = this.entityManager.createQuery(criteriaQuery);
    List<Tuple> results = query.getResultList();

    return QueryProjectionUtils.createProjection(results, projectionType);
  }

  public <S> Page<S> findAll(Pageable pageable, Class<S> projectionType) {
    return this.findAll((Specification) null, (Pageable) pageable, projectionType);
  }

  public <S> Slice<S> findAllSlice(Specification<T> spec, Pageable pageable, Class<S> projectionType) {

    Page<S> result = this.findAll(spec, pageable, projectionType);
    return new SimpleSliceImpl(result.getContent(), pageable, result.hasNext());
  }

  private <S> CriteriaQuery<Tuple> createQuery(Specification<T> spec, Sort sort, Class<S> projectionType) {

    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Tuple> query = criteriaBuilder.createTupleQuery();
    Root<T> root = this.applySpecificationToCriteria(spec, this.getEntityType(), query);
    Set<Selection<T>> selections = QueryProjectionUtils.createProjectedSelection(root, this.getEntityType(),
        projectionType);
    query.multiselect(new ArrayList(selections));
    if (sort != null) {
      query.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));
    }

    return query;
  }

  private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, Class<T> entityClass,
      CriteriaQuery<S> query) {

    Root<T> root = query.from(entityClass);
    if (spec != null) {
      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      Predicate predicate = spec.toPredicate(root, query, builder);
      if (predicate != null) {
        query.where(predicate);
      }
    }

    return root;
  }

  private static Long executeCountQuery(TypedQuery<Long> query) {
    List<Long> totals = query.getResultList();
    long total = 0L;

    for (Long element : totals) {
      total += element == null ? 0L : element;
    }

    return total;
  }

  protected <S> Page<S> readPage(List<S> resultList, Class<T> domainClass, Pageable pageable, Specification<T> spec) {
    return PageableExecutionUtils.getPage(resultList, pageable,
        () -> executeCountQuery(this.getCountQuery(spec, domainClass)));
  }

  private Class<T> getEntityType() {
    return this.getDomainClass();
  }
}
