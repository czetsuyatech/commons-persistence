package com.czetsuyatech.persistence.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@JsonIgnoreProperties({"pageable", "sort"})
public class SimpleSliceImpl<T> extends SliceImpl<T> {

  public SimpleSliceImpl(List<T> content, Pageable pageable, boolean hasNext) {
    super(content, pageable, hasNext);
  }

  public SimpleSliceImpl(List<T> content) {
    super(content);
  }

  public <U> Slice<U> map(Function<? super T, ? extends U> converter) {
    return new SliceImpl<U>(this.getConvertedContent(converter), getPageable(), hasNext());
  }
}
