package com.czetsuyatech.persistence.search;

public enum UserSearchFields {

  FIRSTNAME {
    @Override
    public String toString() {
      return "firstName";
    }
  },
  LASTNAME {
    @Override
    public String toString() {
      return "lastName";
    }
  }
}
