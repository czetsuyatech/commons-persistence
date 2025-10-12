package com.czetsuyatech.persistence.repositories;

import java.io.Serializable;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SliceJpaRepository<ENTITY, ID extends Serializable> extends JpaRepository<ENTITY, ID>,
    JpaSpecificationExecutor<ENTITY>, JpaSpecificationExecutorWithProjection<ENTITY> {

  Slice<ENTITY> findAllSlice(@Nullable Specification<ENTITY> specification, Pageable pageable);
}
