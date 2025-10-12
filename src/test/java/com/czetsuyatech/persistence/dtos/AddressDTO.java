package com.czetsuyatech.persistence.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

  private String city;
  private String country;
}
