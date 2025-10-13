package com.czetsuyatech.persistence.search;

import com.czetsuyatech.persistence.dtos.UserDTO;
import com.czetsuyatech.persistence.entities.UserEntity;
import com.czetsuyatech.persistence.entities.UserEntity_;
import com.czetsuyatech.persistence.search.constant.RelationalOperators;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

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

//    if (null != userDTO.getHobbies() && !userDTO.getHobbies().isEmpty()) {
//      spec = Specification.where(spec)
//          .and((root, query, builder) -> {
//            Join<UserEntity, String> hobbiesJoin = root.join(UserEntity_.hobbies);
//            return hobbiesJoin.in(userDTO.getHobbies());
//          });
//    }

    if (null != userDTO.getOrientations() && userDTO.getOrientations().size() > 0) {
      spec = Specification.where(spec)
          .and(new GenericSpecification<>(new SearchCriteria(UserEntity_.orientation.getName(),
              RelationalOperators.IN.toString(), userDTO.getOrientations())));
    }

    if (null != userDTO.getAddress() && StringUtils.hasLength(userDTO.getAddress().getCountry())) {
      spec = Specification.where(spec)
          .and(new GenericSpecification<>(new SearchCriteria("address.country",
              RelationalOperators.JOIN.toString(), userDTO.getAddress().getCountry())));
    }

    if (null != userDTO.getFavoriteNos() && userDTO.getFavoriteNos().size() > 0) {
      spec = Specification.where(spec)
          .and(new GenericSpecification<>(new SearchCriteria(UserEntity_.favoriteNo.getName(),
              RelationalOperators.IN.toString(), userDTO.getFavoriteNos())));
    }

    return spec;
  }
}
