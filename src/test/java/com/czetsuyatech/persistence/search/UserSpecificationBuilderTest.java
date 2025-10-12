package com.czetsuyatech.persistence.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import com.czetsuyatech.persistence.config.CzetsuyaTechDataJpaTest;
import com.czetsuyatech.persistence.dtos.AddressDTO;
import com.czetsuyatech.persistence.dtos.UserDTO;
import com.czetsuyatech.persistence.entities.OrientationEnum;
import com.czetsuyatech.persistence.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
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
public class UserSpecificationBuilderTest {

  @Autowired
  private UserRepository userRepository;
  private UserSpecificationBuilder userSpecificationBuilder;

  @Test
  void build_shouldReturn1_whenDateIsNotNull() {

    UserDTO userDTO = UserDTO.builder()
        .firstName("czetsuya")
        .lastName("tech")
        .birthDate(LocalDateTime.now())
        .build();

    UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(userDTO);
    userSpecificationBuilder.setNullBirthDate(true);
    var userSpec = userSpecificationBuilder.build();

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
  }

  @Test
  void build_shouldReturnMales_whenOrientationFilterIsSet() {

    UserDTO userDTO = UserDTO.builder()
        .firstName("Edward")
        .lastName("Legaspi")
        .orientations(List.of(OrientationEnum.MALE))
        .birthDate(LocalDateTime.now())
        .build();

    UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(userDTO);
    var userSpec = userSpecificationBuilder.build();

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(10);
  }

  @Test
  void build_shouldReturnUserInPH_whenSearchByCountry() {

    UserDTO userDTO = UserDTO.builder()
        .address(AddressDTO.builder()
            .country("PH")
            .build())
        .build();

    UserSpecificationBuilder userSpecificationBuilder = new UserSpecificationBuilder(userDTO);
    var userSpec = userSpecificationBuilder.build();

    var result = userRepository.findAllSlice(userSpec, Pageable.ofSize(10));

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
  }
}
