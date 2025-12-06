package com.czetsuyatech.persistence.entity;

import java.io.Serializable;

public interface Id<T extends Serializable> {

  T getId();

  void setId(T id);
}
