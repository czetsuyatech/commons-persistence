package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseRefDTO<ID extends Serializable> extends BusinessDTO<ID> implements RefDataDTO<ID> {

  private Integer sortOrder;

  public BaseRefDTO(BusinessDTO<ID> other) {
    super(other);
  }

  @Override
  public RefDataDTO<ID> deepClone() {
    return new BaseRefDTO<>(this);
  }
}
