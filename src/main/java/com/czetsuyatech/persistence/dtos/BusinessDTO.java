package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessDTO<ID extends Serializable> extends EnableDTO<ID> {

  private String code;
  private String description;

  public BusinessDTO(BusinessDTO<ID> other) {

    setId(other.getId());
    this.code = other.code;
    this.description = other.description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BusinessDTO<?> that)) {
      return false;
    }
    return Objects.equals(getCode(), that.getCode());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getCode());
  }
}
