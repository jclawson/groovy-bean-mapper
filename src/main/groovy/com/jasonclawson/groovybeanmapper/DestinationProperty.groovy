package com.jasonclawson.groovybeanmapper;

import java.lang.reflect.Field;

import com.jasonclawson.groovybeanmapper.util.FieldUtils;

public class DestinationProperty<T> {
	
	Class<?> type;
	def setters = new HashMap();
	
	public DestinationProperty(Class<?> type) {
		this.type = type;
	}
	
    //setter prop missing
	def propertyMissing(String name, Object value) {
		println("set "+name+" to "+value+" on "+type.getSimpleName());
        setters[name] = value;
	}
}
