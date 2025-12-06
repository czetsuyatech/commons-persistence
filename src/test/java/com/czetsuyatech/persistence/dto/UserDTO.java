package com.czetsuyatech.persistence.dto;

import com.czetsuyatech.persistence.entity.OrientationEnum;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

  private String firstName;
  private String lastName;
  private LocalDateTime birthDate;
  private OrientationEnum orientation;
  private List<String> hobbies;
  private AddressDTO address;

  private List<OrientationEnum> orientations;
  private List<Integer> favoriteNos;
}
