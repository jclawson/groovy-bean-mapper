package com.jasonclawson.groovybeanmapper

class MappingProxy {
	Class<?> type;
	MappingProxy(Class<?> type) {
		this.type = type;
		println("Proxy() for "+type.getSimpleName())
	}
	
	//TODO: make work for private fields
	def propertyMissing(String name) {
		println("get "+name+" on "+type.getSimpleName());
		def propType = type.metaClass.properties.find { it.name == name }.type;
		return new MappingProxy(propType);
	}
	
	def propertyMissing(String name, value) {
		println("set "+name+" to "+value+" on "+type.getSimpleName());
	}
}
