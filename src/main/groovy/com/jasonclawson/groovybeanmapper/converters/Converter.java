package com.jasonclawson.groovybeanmapper.converters;

public interface Converter<F,T> {
	public T convert(F from);
}
