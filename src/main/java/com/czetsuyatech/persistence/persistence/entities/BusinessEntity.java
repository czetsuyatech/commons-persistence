package com.czetsuyatech.persistence.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public abstract class BusinessEntity extends EnableEntity {

  @Column(name = "code", nullable = false, length = 50)
  @Size(max = 50, min = 1)
  @NotNull
  protected String code;

  @Column(name = "name", nullable = true, length = 255)
  @Size(max = 255)
  protected String name;
}
