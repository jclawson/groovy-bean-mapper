package com.jasonclawson.groovybeanmapper.v2

import groovy.lang.Closure;

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class SourceProperty<F> {
	
	SourceProperty parent;
	Class<?> type;
	String name;
	
	Closure[] conditions;
	
	SourceProperty(Class<?> type) {
		this.type = type;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type) {
		this.parent = parent;
		this.type = type;
		this.name = name;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type, Closure[] conditions) {
		this(parent, name, type);
		this.conditions = conditions;
	}
	
	def propertyMissing(String name) {
		println("get "+name+" on "+type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
		def propType =  f.type;
		return new SourceProperty(this, name, propType);
	}
	
	def methodMissing(String name, args) {
		println("method missing "+name+" on "+type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
		def propType =  f.type;
		return new SourceProperty(this, name, propType, (Closure[])args);
	}
	
	public F get(MappingContext context, Object instance){		
		if(name == null)
			return instance;
		
		if(parent != null) {
			context.push(name);
			instance = parent.get(context, instance);
			context.pop();
		}
		
		if(instance == null)
			return null;

		return instance[name];
	}
	
	public boolean isSkip(MappingContext context, Object instance) {
		if(conditions != null) {
			for(int i=0; i<conditions.length; i++) {
				if(!conditions[i].call(context, instance)) {
					return true;
				}
			}
		}
		return false;
	}
}
