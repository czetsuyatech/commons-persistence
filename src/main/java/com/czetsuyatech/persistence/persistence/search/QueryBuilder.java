package com.czetsuyatech.persistence.persistence.search;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.springframework.data.jpa.domain.Specification;

public class QueryBuilder {

  private static final Pattern pattern = Pattern.compile("(\\w+?)(<=|>=|[:<>/%~])(.+?),");

  private QueryBuilder() {

  }

  public static <T, V> Specification<T> build(String search, Class<V> specification, Class<? extends Enum<?>> e)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

    List<SearchCriteria> params = new ArrayList<>();
    Matcher matcher = pattern.matcher(search + ",");

    while (matcher.find()) {
      getListParams(matcher.group(1), matcher.group(2), matcher.group(3), params, e);
    }

    if (params.isEmpty()) {
      return null;
    }

    SearchCriteria first = params.get(0);
    Constructor<V> constructor = specification.getConstructor(SearchCriteria.class);
    Specification<T> result = (Specification<T>) constructor.newInstance(first);

    for (int i = 1; i < params.size(); i++) {
      result = Specification.where(result)
          .and((Specification<T>) constructor.newInstance(params.get(i)));
    }

    return result;
  }

  private static void getListParams(String key, String operation, Object value, List<SearchCriteria> params,
      Class<? extends Enum<?>> e) {

    List<String> availableFields = Stream.of(e.getEnumConstants())
        .map(Enum::toString)
        .toList();
    if (availableFields.contains(key)) {
      params.add(new SearchCriteria(key, operation, value));
    }
  }
}
