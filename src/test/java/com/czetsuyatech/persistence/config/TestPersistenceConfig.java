package com.czetsuyatech.persistence.config;

import com.czetsuyatech.persistence.entities.CtEntitiesConfig;
import com.czetsuyatech.persistence.repositories.CtRepositoriesConfig;
import com.czetsuyatech.persistence.repositories.SimpleSliceJpaRepositoryImpl;
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
