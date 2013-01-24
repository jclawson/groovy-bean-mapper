package com.jasonclawson.groovybeanmapper.v2

import groovy.transform.EqualsAndHashCode



class BeanMapping {
	
	private Map<MappingKey, Mapping> mappers = new HashMap();
	
	static class SingletonHolder {
		static final BeanMapping INSTANCE = new BeanMapping();
	}
	
	@EqualsAndHashCode
	class MappingKey {
		Class from
		Class to
	}
	
	private BeanMapping(){
		
	}
	
	public static BeanMapping getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public void createMapping(Class fromClass, Class toClass, Closure closure) {
		
	}
	
	public void createConverter(Class fromClass, Class toClass, Closure converter) {
		
	}
}
