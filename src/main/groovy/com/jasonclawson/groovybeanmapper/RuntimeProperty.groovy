package com.jasonclawson.groovybeanmapper

import com.jasonclawson.groovybeanmapper.converters.Converter

/**
 * FIXME: we need an AbstractProperty class to put the usingConverter method on!
 * @author jclawson
 *
 */
class RuntimeProperty {
	private String key;
	private Converter converter;
	
	public RuntimeProperty(String key) {
		this.key = key;
	}
	
	public String _$getName() {
		return key;
	}
	
	public Object get(MappingContext context, Object instance) {
		Object value = context.getRuntimeValue(key);
		if(converter != null) {
			value = converter.convert(value);
		}
		return value;
	}
	
	public boolean isSkip(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
		return false;
	}
	
	public RuntimeProperty usingConverter(Converter closure) {
		this.converter = closure;
		return this;
	}
}
