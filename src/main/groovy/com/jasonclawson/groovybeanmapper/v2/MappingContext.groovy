package com.jasonclawson.groovybeanmapper.v2

import java.util.List;
import java.util.Map;

class MappingContext {
	List<String> includeMapOnly;
	Map<String, Object> valueOverrides;
	
	//TODO: implement full support for nested includes / overrides
	private int level = 0;
	
	public void push(String propertyName) {
		level++;
	}
	
	public String pop() {
		level--;
	}
	
	boolean isSkip(String key) {
		if(includeMapOnly == null || level != 0)
			return true;
		
		return !includeMapOnly.contains(key);
	}
	
	Object getValue(String name, Object value) {
		if(valueOverrides == null || level != 0)
			return value;
		
		Object v2 = valueOverrides.get(name);
		return (v2 == null)?value:v2;
	}
}
