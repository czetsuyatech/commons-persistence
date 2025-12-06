package com.czetsuyatech.persistence.dto;

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
public class EnableDTO extends AuditableDTO {

  private boolean activeStatus;

  public boolean isEnabled() {
    return activeStatus;
  }
}
