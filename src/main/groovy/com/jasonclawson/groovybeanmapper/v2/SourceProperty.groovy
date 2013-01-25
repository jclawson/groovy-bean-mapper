package com.jasonclawson.groovybeanmapper.v2

import groovy.lang.Closure;

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class SourceProperty<F> {
	
	SourceProperty parent;
	Class<?> type;
	String name;
	
	Predicate predicate;
	
	SourceProperty(Class<?> type) {
		this.type = type;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type) {
		this.parent = parent;
		this.type = type;
		this.name = name;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type, Predicate predicate) {
		this(parent, name, type);
		this.predicate = predicate;
	}
	
	def propertyMissing(String name) {
		println("get "+name+" on "+type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
		def propType =  f.type;
		return new SourceProperty(this, name, propType);
	}
	
	def methodMissing(String name, args) {
		println("method missing "+name+" on "+type.getSimpleName()+" with "+args.length+" arguments");
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
		def propType =  f.type;
		//arg[0] will be a predicate... like "when"
		return new SourceProperty(this, name, propType, args[0]);
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
	
	public boolean isSkip(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
		if(predicate != null) {
			return !predicate.check(context, instance, fromPropertyName, toPropertyName);
		}
		return false;
	}
}
