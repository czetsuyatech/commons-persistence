package com.czetsuyatech.persistence.dtos;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

  private String firstName;
  private String lastName;
  private LocalDateTime birthDate;
}
