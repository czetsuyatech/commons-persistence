package com.czetsuyatech.persistence;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}
