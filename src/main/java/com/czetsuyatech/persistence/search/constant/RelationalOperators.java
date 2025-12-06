package com.czetsuyatech.persistence.search.constant;

public enum RelationalOperators {

  GREATER {
    @Override
    public String toString() {
      return ">";
    }
  },
  LESS {
    @Override
    public String toString() {
      return "<";
    }
  },
  EQUAL {
    @Override
    public String toString() {
      return ":";
    }
  },
  NOTEQUAL {
    @Override
    public String toString() {
      return "<>";
    }
  },
  GREATER_THAN_EQUAL {
    @Override
    public String toString() {
      return ">=";
    }
  },
  LESS_THAN_EQUAL {
    @Override
    public String toString() {
      return "<=";
    }
  },
  ISNULL {
    @Override
    public String toString() {
      return "~";
    }
  },
  NOTNULL {
    @Override
    public String toString() {
      return "~~";
    }
  },
  LIKE {
    @Override
    public String toString() {
      return "*";
    }
  },
  IN {
    @Override
    public String toString() {
      return "^";
    }
  },
  JOIN {
    @Override
    public String toString() {
      return ">>";
    }
  };

  public static RelationalOperators getOperator(String value) {
    for (RelationalOperators operator : values()) {
      if (operator.toString().equals(value)) {
        return operator;
      }
    }

    throw new IllegalArgumentException(String.format("Operator %s not found.", value));
  }
}
