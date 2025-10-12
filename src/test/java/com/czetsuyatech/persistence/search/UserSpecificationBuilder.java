package com.czetsuyatech.persistence.search;

import com.czetsuyatech.persistence.dtos.UserDTO;
import com.czetsuyatech.persistence.entities.UserEntity;
import com.czetsuyatech.persistence.entities.UserEntity_;
import com.czetsuyatech.persistence.search.constant.RelationalOperators;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecificationBuilder extends AbstractSpecificationsBuilder<UserEntity> {

  private final UserDTO userDTO;
  private Boolean nullBirthDate;

  public UserSpecificationBuilder(final UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  public void setNullBirthDate(final Boolean nullBirthDate) {
    this.nullBirthDate = nullBirthDate;
  }

  @Override
  public Specification<UserEntity> build() {

    Specification<UserEntity> spec = Specification.unrestricted();

    if (nullBirthDate != null && nullBirthDate) {
      spec = Specification.where(spec)
          .and(new GenericSpecification<>(new SearchCriteria(UserEntity_.birthDate.getName(),
              RelationalOperators.NOTNULL.toString(), userDTO.getBirthDate())));
    }

    return spec;
  }
}
