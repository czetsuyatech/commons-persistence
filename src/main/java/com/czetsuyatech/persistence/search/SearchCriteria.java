package com.czetsuyatech.persistence.search;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * K/V pair with operator. Operators are:
 * <ul>
 * <li>: - equals</li>
 * <li>< - less than</li>
 * <li>> - greater than</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class SearchCriteria {

  private String key;
  private String operation;
  private Object value;
}
