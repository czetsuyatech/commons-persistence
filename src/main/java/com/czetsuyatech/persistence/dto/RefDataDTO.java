package com.czetsuyatech.persistence.dto;

import java.io.Serializable;

public interface RefDataDTO<ID extends Serializable> {

  String getCode();

  boolean isEnabled();

  Integer getSortOrder();
}
