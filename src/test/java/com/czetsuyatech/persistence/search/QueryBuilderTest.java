package com.czetsuyatech.persistence.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import com.czetsuyatech.persistence.config.CzetsuyaTechDataJpaTest;
import com.czetsuyatech.persistence.entity.UserEntity;
import com.czetsuyatech.persistence.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(classes = {UserRepository.class})
@ActiveProfiles("tst")
@DirtiesContext(classMode = AFTER_CLASS)
@Sql(value = {"/sql/users.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@CzetsuyaTechDataJpaTest
@Slf4j
class QueryBuilderTest {

  @Autowired
  private UserRepository userRepository;

  @SneakyThrows
  @Test
  void build_shouldReturnUnrestricted_whenSearchIsEmpty() {

    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build("", UserSpecification.class,
        UserSearchFields.class);

    assertThat(userSpec).isEqualTo(Specification.unrestricted());
  }

  @SneakyThrows
  @Test
  void build_shouldReturnUnrestricted_whenParamIsNull() {

    var searchParams = "firstNamexEd";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    assertThat(userSpec).isEqualTo(Specification.unrestricted());
  }

  @SneakyThrows
  @Test
  void build_shouldReturnEmptySlice_whenFirstNameIsNotMatched() {

    var searchParams = "firstName:Ed";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(0);
  }

  @SneakyThrows
  @Test
  void build_shouldReturnUser_whenFirstNameIsMatched() {

    var searchParams = "firstName:Edward";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get().findFirst().get().getLastName()).isEqualTo("Legaspi");
  }

  @SneakyThrows
  @Test
  void build_shouldReturnUser_whenFirstAndLastNameMatched() {

    var searchParams = "firstName:Edward";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get().findFirst().get().getFirstName()).isEqualTo("Edward");
    assertThat(result.get().findFirst().get().getLastName()).isEqualTo("Legaspi");
  }

  @SneakyThrows
  @Test
  void build_shouldReturnUser_whenFirstNameLikeIsMatched() {

    var searchParams = "firstName*ar";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(5);
    assertThat(result.get().findFirst().get().getFirstName()).isEqualTo("Edward");
    assertThat(result.get().findFirst().get().getLastName()).isEqualTo("Legaspi");
  }

  @SneakyThrows
  @Test
  void build_shouldReturnUser_whenLastNameLikeIsMatched() {

    var searchParams = "lastName*ar";
    var userSpec = QueryBuilder.<UserEntity, UserSpecification>build(searchParams, UserSpecification.class,
        UserSearchFields.class);

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result.get().findFirst().get().getFirstName()).isEqualTo("Frank");
    assertThat(result.get().findFirst().get().getLastName()).isEqualTo("Garcia");
  }
}
