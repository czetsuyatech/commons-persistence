package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class BusinessDTO<ID extends Serializable> extends EnableDTO implements RefDataDTO<ID> {

  private String code;
  private String description;

  @Override
  public boolean isEnabled() {
    return super.isEnabled();
  }

  @Override
  public Integer getSortOrder() {
    return 0;
  }
}
