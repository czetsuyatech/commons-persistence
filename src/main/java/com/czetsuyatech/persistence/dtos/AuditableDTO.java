package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AuditableDTO<ID extends Serializable> extends BaseDTO<ID> {

  private Instant created;
  private Instant updated;
  private String createdBy;
  private String updatedBy;
}
