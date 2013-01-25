package com.jasonclawson.groovybeanmapper.v2

import java.util.List;
import java.util.Map;

class MappingContext {
	List<String> includeMapOnly;
	Map<String, Object> runtimeValues;
	
	LinkedList<String> path = new LinkedList();
	
	public void push(String propertyName) {
		path.push(propertyName);
	}
	
	public String pop() {
		path.pop();
	}
	
	public String getPropertyPath(String propertyName) {
		String parent = path.join(".");
		parent = (parent.size() == 0)?parent:parent+".";
		return parent+propertyName;
	}
	
	boolean isSkip(String key) {
		if(includeMapOnly == null)
			return false;
		
		return !includeMapOnly.contains(key);
	}
	
	public Object getRuntimeValue(String name) {
		if(runtimeValues == null)
			return null;		
		return runtimeValues.get(name);
	}
}
