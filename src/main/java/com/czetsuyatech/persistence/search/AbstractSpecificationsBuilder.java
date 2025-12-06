package com.czetsuyatech.persistence.search;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSpecificationsBuilder<T> {

  private final List<SearchCriteria> params = new ArrayList<>();

  public AbstractSpecificationsBuilder() {

  }

  public Specification<T> build() {

    Specification<T> result = Specification.unrestricted();

    for (SearchCriteria param : params) {
      result = Specification.where(result).and(new GenericSpecification<>(param));
    }

    return result;
  }

  protected void with(final String key, final String operation, final Object value) {

    params.add(new SearchCriteria(key, operation, value));
  }
}
