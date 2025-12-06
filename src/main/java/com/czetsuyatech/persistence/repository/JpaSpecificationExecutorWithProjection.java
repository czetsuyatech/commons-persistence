package com.czetsuyatech.persistence.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface JpaSpecificationExecutorWithProjection<T> {

  /**
   * Returns a single entity matching the given {@link Specification}.
   *
   * @param spec
   * @param projectionType
   * @return
   */
  <S> S findOne(Specification<T> spec, Class<S> projectionType);

  /**
   * Returns all entity matching the given {@link Specification}.
   *
   * @param spec
   * @param projectionType
   * @return
   */
  <S> List<S> findAll(Specification<T> spec, Class<S> projectionType);

  /**
   * Returns a {@link Page} of entity matching the given {@link Specification}.
   *
   * @param spec
   * @param pageable
   * @param projectionType
   * @return
   */
  <S> Page<S> findAll(Specification<T> spec, Pageable pageable, Class<S> projectionType);

  /**
   * Returns all entity matching the given {@link Specification} and {@link Sort}.
   *
   * @param spec
   * @param sort
   * @param projectionType
   * @return
   */
  <S> List<S> findAll(Specification<T> spec, Sort sort, Class<S> projectionType);

  /**
   * Returns a {@link Page} of entity.
   *
   * @param pageable
   * @param projectionType
   * @return
   */
  <S> Page<S> findAll(Pageable pageable, Class<S> projectionType);

  /**
   * Returns a {@link Slice} of entity
   *
   * @param spec
   * @param pageable
   * @param projectionType
   * @param <S>
   * @return
   */
  <S> Slice<S> findAllSlice(Specification<T> spec, Pageable pageable, Class<S> projectionType);

  /**
   * Returns the number of instances that the given {@link Specification} will return.
   *
   * @param spec the {@link Specification} to count instances for
   * @return the number of instances
   */
  long count(Specification<T> spec);
}
