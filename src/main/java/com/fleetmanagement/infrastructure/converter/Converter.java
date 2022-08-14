package com.fleetmanagement.infrastructure.converter;

import java.util.List;

public interface Converter<T, R> {

    R from(T source);

    T to(R source);

    List<R> fromList(List<T> sources);

    List<T> toList(List<R> sources);

}
