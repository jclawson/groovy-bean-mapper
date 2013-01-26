package com.jasonclawson.groovybeanmapper

import groovy.lang.Closure;

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class SourceProperty<F> {
	
	SourceProperty _$parent;
	Class<?> _$type;
	String _$name;
	
	Predicate _$predicate;
	
	SourceProperty(Class<?> type) {
		this._$type = type;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type) {
		this(type);
		this._$parent = parent;
		this._$name = name;
	}
	
	SourceProperty(SourceProperty parent, String name, Class<?> type, Predicate predicate) {
		this(parent, name, type);
		this._$predicate = predicate;
	}
	
	public String _$getName() {
		return _$name;
	}
	
	def propertyMissing(String name) {
		println("get "+name+" on "+_$type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, _$type);
		def propType =  f.type;
		return new SourceProperty(this, name, propType);
	}
	
	def methodMissing(String name, args) {
		println("method missing "+name+" on "+_$type.getSimpleName()+" with "+args.length+" arguments");
		Field f = FieldUtils.getInheritedDeclaredField(name, _$type);
		def propType =  f.type;
		//arg[0] will be a predicate... like "when"
		return new SourceProperty(this, name, propType, args[0]);
	}
	
	public F get(MappingContext context, Object instance){		
		if(_$name == null)
			return instance;
		
		if(_$parent != null) {
			context.push(_$name);
			instance = _$parent.get(context, instance);
			context.pop();
		}
		
		if(instance == null)
			return null;

		return instance[_$name];
	}
	
	public boolean isSkip(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
		if(_$predicate != null) {
			return !_$predicate.check(context, instance, fromPropertyName, toPropertyName);
		}
		return false;
	}
}
