package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class EnableDTO<ID extends Serializable> extends AuditableDTO<ID> {

  private boolean disabled;

  public boolean isEnabled() {
    return !disabled;
  }
}
