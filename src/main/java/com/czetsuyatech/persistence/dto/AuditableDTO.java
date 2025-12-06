package com.czetsuyatech.persistence.dto;

import java.time.Instant;
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
public abstract class AuditableDTO extends BaseDTO {

  private Instant created;
  private Instant updated;
  private String createdBy;
  private String updatedBy;
}
