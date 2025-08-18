package com.czetsuyatech.persistence.persistence.entities;

import java.io.Serializable;

public interface IEntity {

  Serializable getId();

  boolean isTransient();
}
