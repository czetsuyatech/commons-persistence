package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseDTO<ID extends Serializable> {

  private ID id;
}
