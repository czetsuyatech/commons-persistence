package com.czetsuyatech.persistence.config;

import com.czetsuyatech.persistence.entity.CtEntitiesConfig;
import com.czetsuyatech.persistence.repository.CtRepositoriesConfig;
import com.czetsuyatech.persistence.repository.SimpleSliceJpaRepositoryImpl;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
    repositoryBaseClass = SimpleSliceJpaRepositoryImpl.class,
    basePackageClasses = {
        CtRepositoriesConfig.class
    })
@EntityScan(basePackageClasses = {CtEntitiesConfig.class})
@EnableTransactionManagement
public class TestPersistenceConfig {

}
