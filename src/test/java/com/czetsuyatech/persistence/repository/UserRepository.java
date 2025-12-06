package com.czetsuyatech.persistence.repository;

import com.czetsuyatech.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SliceJpaRepository<UserEntity, Long> {

}
