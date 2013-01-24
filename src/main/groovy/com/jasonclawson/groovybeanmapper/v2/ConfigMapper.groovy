package com.jasonclawson.groovybeanmapper.v2

import java.util.List;
import java.util.Map;

import com.jasonclawson.groovybeanmapper.MappingProxy;

class ConfigMapper<F,T> implements Mapping<F,T> {
	MappingProxy from;
	MappingProxy to;
	
	public T map(F instance) {
		// TODO Auto-generated method stub
		return null;
	}
	public T mapFiltered(F instance, List<String> includeProperties,
			Map<String, Object> overrides) {
		// TODO Auto-generated method stub
		return null;
	}
	public T map(F instance, T toInstance) {
		// TODO Auto-generated method stub
		return null;
	}
}
