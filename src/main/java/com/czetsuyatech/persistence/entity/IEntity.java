package com.czetsuyatech.persistence.entity;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}
