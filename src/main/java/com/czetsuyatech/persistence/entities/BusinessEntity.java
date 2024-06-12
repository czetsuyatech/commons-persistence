package com.czetsuyatech.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BusinessEntity extends EnableEntity {

  @Column(name = "code", nullable = false, length = 50)
  @Size(max = 50, min = 1)
  @NotNull
  protected String code;

  @Column(name = "description", nullable = true, length = 255)
  @Size(max = 255)
  protected String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BusinessEntity that)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    return Objects.equals(getCode(), that.getCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getCode());
  }
}
