package com.jasonclawson.groovybeanmapper.v2


class RuntimeProperty {
	private String key;
	
	public RuntimeProperty(String key) {
		this.key = key;
	}
	
	public Object get(MappingContext context, Object instance) {
		return context.getRuntimeValue(key);
	}
	
	public boolean isSkip(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
		return false;
	}
}
