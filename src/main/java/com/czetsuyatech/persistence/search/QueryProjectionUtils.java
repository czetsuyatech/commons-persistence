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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

public class QueryProjectionUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(QueryProjectionUtils.class);
  private static final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

  private QueryProjectionUtils() {
  }

  public static <T, R> Set<Selection<T>> createProjectedSelection(Root<T> root, Class<T> rootType,
      Class<R> projectionType) {

    Set<Selection<T>> selections = new HashSet<>();
    List<PropertyDescriptor> inputProperties = projectionFactory.getProjectionInformation(projectionType)
        .getInputProperties();
    inputProperties.forEach((propertyDescriptor) -> {
      String property = propertyDescriptor.getName();
      selections.add((Selection<T>) toExpressionRecursively(root, rootType, property).alias(property));
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

  public static <T> Expression<T> toExpressionRecursively(From<?, ?> from, Class<?> rootType, String property) {

    Class<QueryUtils> queryUtilsClass = QueryUtils.class;

    // 1) Try Spring Data JPA newer signature: toExpressionRecursively(From, String)
    try {
      Method method = queryUtilsClass.getDeclaredMethod("toExpressionRecursively", From.class, String.class);
      method.setAccessible(true);

      return (Expression<T>) method.invoke(null, from, property);

    } catch (NoSuchMethodException ignored) {
      // continue to try older signature

    } catch (IllegalAccessException | InvocationTargetException e) {
      LOGGER.error("Error invoking QueryUtils.toExpressionRecursively(From, String)", e);
    }

    // 2) Try older signature using PropertyPath via reflection to avoid hard dependency
    try {
      Class<?> propertyPathClass = Class.forName("org.springframework.data.mapping.PropertyPath");
      Method fromMethod = propertyPathClass.getMethod("from", String.class, Class.class);
      Object propertyPath = fromMethod.invoke(null, property, rootType);
      Method legacy = queryUtilsClass.getDeclaredMethod("toExpressionRecursively", From.class, propertyPathClass);
      legacy.setAccessible(true);

      return (Expression<T>) legacy.invoke(null, from, propertyPath);

    } catch (ClassNotFoundException | NoSuchMethodException ignored) {
      // fall back to manual resolution

    } catch (IllegalAccessException | InvocationTargetException e) {
      LOGGER.error("Error invoking QueryUtils.toExpressionRecursively(From, PropertyPath)", e);
    }

    // 3) Fallback: manual path resolution
    return (Expression<T>) resolvePath(from, property);
  }

  private static Expression<?> resolvePath(From<?, ?> from, String property) {
    String[] parts = property.split("\\.");
    jakarta.persistence.criteria.Path<?> path = from;

    for (String part : parts) {
      // If current path is a From, try to use existing joins first
      if (path instanceof From<?, ?> currentFrom) {
        From<?, ?> match = null;
        for (jakarta.persistence.criteria.Join<?, ?> join : currentFrom.getJoins()) {
          if (join.getAttribute() != null && part.equals(join.getAttribute().getName())) {
            match = (From<?, ?>) join;
            break;
          }
        }
        if (match != null) {
          path = match;
          continue;
        }
      }
      path = path.get(part);
    }

    return (Expression<?>) path;
  }

  private static Map<String, Object> createMappedResult(Tuple tuple) {

    Map<String, Object> mappedResult = new HashMap<>(tuple.getElements().size());
    tuple.getElements().forEach((tupleElement) -> {
      String name = tupleElement.getAlias();
      mappedResult.put(name, tuple.get(name));
    });

    return mappedResult;
  }
}
