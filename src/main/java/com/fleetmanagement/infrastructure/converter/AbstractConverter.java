package com.fleetmanagement.infrastructure.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<T, R> implements Converter<T, R> {

    @Override
    public List<R> fromList(List<T> sources) {
        return sources.stream().map(this::from).collect(Collectors.toList());
    }

    @Override
    public List<T> toList(List<R> sources) {
        return sources.stream().map(this::to).collect(Collectors.toList());
    }
}
