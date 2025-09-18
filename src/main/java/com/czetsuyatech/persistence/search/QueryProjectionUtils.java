package com.czetsuyatech.persistence.search;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

public class QueryProjectionUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueryProjectionUtils.class);
  private static final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

  private QueryProjectionUtils() {
  }

  public static <T, R> Set<Selection<T>> createProjectedSelection(Root<T> root, Class<T> rootType,
      Class<R> projectionType) {

    Set<Selection<T>> selections = new HashSet();
    List<PropertyDescriptor> inputProperties = projectionFactory.getProjectionInformation(projectionType)
        .getInputProperties();
    inputProperties.forEach((propertyDescriptor) -> {
      String property = propertyDescriptor.getName();
      PropertyPath path = PropertyPath.from(property, rootType);
      selections.add((Selection<T>) toExpressionRecursively(root, path).alias(property));
    });

    return selections;
  }

  public static <T> T createProjection(Tuple tuple, Class<T> projectionType) {

    Map<String, Object> mappedResult = createMappedResult(tuple);
    return (T) projectionFactory.createProjection(projectionType, mappedResult);
  }

  public static <T> List<T> createProjection(List<Tuple> tuples, Class<T> projectionType) {

    return tuples.stream()
        .map(tuple -> createProjection(tuple, projectionType))
        .toList();
  }

  public static <T> Expression<T> toExpressionRecursively(From<?, ?> from, PropertyPath property) {

    Class<QueryUtils> queryUtilsClass = QueryUtils.class;

    try {
      Method method = queryUtilsClass.getDeclaredMethod("toExpressionRecursively", From.class, PropertyPath.class);
      method.setAccessible(true);
      return (Expression<T>) method.invoke(queryUtilsClass, from, property);

    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      LOGGER.error("Error on generating expression recursively.", e);

      return null;
    }
  }

  private static Map<String, Object> createMappedResult(Tuple tuple) {

    Map<String, Object> mappedResult = new HashMap(tuple.getElements().size());
    tuple.getElements().forEach((tupleElement) -> {
      String name = tupleElement.getAlias();
      mappedResult.put(name, tuple.get(name));
    });

    return mappedResult;
  }
}
