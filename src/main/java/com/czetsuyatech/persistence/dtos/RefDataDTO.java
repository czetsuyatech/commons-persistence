package com.czetsuyatech.persistence.dtos;

import java.io.Serializable;

public interface RefDataDTO<ID extends Serializable> {

  String getCode();

  String getName();

  boolean isEnabled();

  Integer getSortOrder();
}
