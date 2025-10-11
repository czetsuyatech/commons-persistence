package com.czetsuyatech.persistence.search;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.springframework.data.jpa.domain.Specification;

public class QueryBuilder {

  private static final Pattern pattern = Pattern.compile("(\\w+?)(<=|>=|[:<>/*~])(.+?),");

  private QueryBuilder() {

  }

  public static <T, V> Specification<T> build(String search, Class<V> specification, Class<? extends Enum<?>> e)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

    if (search == null || search.isBlank()) {
      return Specification.unrestricted();
    }

    List<SearchCriteria> params = new ArrayList<>();
    Matcher matcher = pattern.matcher(search + ",");

    while (matcher.find()) {
      getListParams(matcher.group(1), matcher.group(2), matcher.group(3), params, e);
    }

    if (params.isEmpty()) {
      return Specification.unrestricted();
    }

    Constructor<V> constructor = specification.getConstructor(SearchCriteria.class);
    Specification<T> result = Specification.unrestricted();

    for (int i = 0; i < params.size(); i++) {
      result = result.and((Specification<T>) constructor.newInstance(params.get(i)));
    }

    return result;
  }

  private static void getListParams(String key, String operation, Object value, List<SearchCriteria> params,
      Class<? extends Enum<?>> e) {

    if (key == null || key.isBlank() || operation == null || operation.isBlank() || value == null) {
      return;
    }

    // If no whitelist enum provided, accept all keys; otherwise validate against enum names
    if (e == null) {
      params.add(new SearchCriteria(key, operation, value));
      return;
    }

    List<String> availableFields = Stream.of(e.getEnumConstants())
        .map(Enum::toString)
        .toList();
    if (availableFields.contains(key)) {
      params.add(new SearchCriteria(key, operation, value));
    }
  }
}
