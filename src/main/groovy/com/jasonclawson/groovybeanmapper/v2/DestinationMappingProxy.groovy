package com.jasonclawson.groovybeanmapper.v2;

import java.lang.reflect.Field;

import com.jasonclawson.groovybeanmapper.GetterProxy;
import com.jasonclawson.groovybeanmapper.MappingContext;
import com.jasonclawson.groovybeanmapper.util.FieldUtils;

public class DestinationMappingProxy<T> {
	
	Class<?> type;
	def setters = new HashMap();
	
	public DestinationMappingProxy(Class<?> type) {
		this.type = type;
	}
	
    //setter prop missing
	def propertyMissing(String name, Object value) {
		println("set "+name+" to "+value+" on "+type.getSimpleName());
        setters[name] = value;
	}
}