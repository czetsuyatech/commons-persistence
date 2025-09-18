package com.czetsuyatech.persistence.repositories;

import com.czetsuyatech.persistence.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SliceJpaRepository<UserEntity, Long> {

}
