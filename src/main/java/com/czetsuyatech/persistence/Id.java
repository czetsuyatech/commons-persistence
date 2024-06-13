package com.czetsuyatech.persistence;

import java.io.Serializable;

public interface Id<T extends Serializable> {

  T getId();

  void setId(T id);
}
