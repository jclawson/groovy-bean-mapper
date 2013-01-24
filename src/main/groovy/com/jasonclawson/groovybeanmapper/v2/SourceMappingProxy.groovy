package com.jasonclawson.groovybeanmapper.v2

import groovy.lang.Closure;

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.MappingContext;
import com.jasonclawson.groovybeanmapper.util.FieldUtils

class SourceMappingProxy<F> {
	
	SourceMappingProxy parent;
	Class<?> type;
	String name;
	
	SourceMappingProxy(Class<?> type) {
		this.type = type;
	}
	
	SourceMappingProxy(SourceMappingProxy parent, String name, Class<?> type) {
		this.parent = parent;
		this.type = type;
		this.name = name;
	}
	
	def propertyMissing(String name) {
		println("get "+name+" on "+type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
		def propType =  f.type;
		return new SourceMappingProxy(this, name, propType);
	}
	
	public F get(MappingContext context, Object instance){		
		if(parent != null) {
			context.push(name);
			instance = parent.get(context, instance);
			context.pop();
		}
		
		if(instance == null)
			return null;

		return instance[name];
	}
	
//	protected boolean shouldSkip(MappingContext context, Object instance) {
//		for(int i =0; i<validators.size(); i++) {
//			if(!validators[i].call(context, instance, name, type)) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	def when(Closure closure) {
		validators.add({
			def obj = [
				
			];
			
			closure.setDelegate(obj);
			closure.call();
		});
	}
}
