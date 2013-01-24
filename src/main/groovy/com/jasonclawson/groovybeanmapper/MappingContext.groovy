package com.jasonclawson.groovybeanmapper

class MappingContext {
    Class<?> sourceType;
    Class<?> destinationType;
    
    Object sourceInstance;
	List<String> filteredProperties;
	Map<String, Object> overrides;
	
	boolean isFiltered(String key) {
		if(filteredProperties == null)
			return true;
		
		return filteredProperties.contains(key);
	}
	
	Object getValue(String name, Object value) {
		if(overrides == null)
			return value;
		
		Object v2 = overrides.get(name);
		return (v2 == null)?value:v2;
	}
}
