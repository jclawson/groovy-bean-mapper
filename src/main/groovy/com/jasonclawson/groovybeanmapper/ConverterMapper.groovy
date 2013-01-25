package com.jasonclawson.groovybeanmapper

import java.util.List;
import java.util.Map;

class ConverterMapper<F,T> extends Mapper<F,T> {

	private Closure converterClosure;
	
	public ConverterMapper(Closure closure) {
		this.converterClosure = converterClosure;
	}
	
	public T map(Object instance, MappingContext context) {
		return converterClosure.call(instance);
	}

	public T map(Object instance, Object toInstance, MappingContext context) {
		return converterClosure.call(instance);
	}

}
