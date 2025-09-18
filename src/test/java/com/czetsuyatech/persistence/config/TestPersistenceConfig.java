package com.czetsuyatech.persistence.config;

import com.czetsuyatech.persistence.repositories.RepositoriesConfig;
import com.czetsuyatech.persistence.repositories.SimpleSliceJpaRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(
    repositoryBaseClass = SimpleSliceJpaRepositoryImpl.class,
    basePackageClasses = {
        RepositoriesConfig.class
    })

@EntityScan(basePackages = {"com.czetsuyatech.persistence.entities"})
@EnableTransactionManagement
public class TestPersistenceConfig {

}
