package com.jasonclawson.groovybeanmapper

import java.util.List;

class Mapper {
    MappingProxy from;
    MappingProxy to;
    
    public Mapper(Object o) {
        this.from = o.from;
        this.to = o.to;
    }
    
    protected Object map(Object instance) {
        Object toInstance = to.type.newInstance();        
        map(instance, toInstance);
		return toInstance;
    }
	
	protected Object mapFiltered(Object instance, List<String> filteredProperties, Map<String, Object> overrides) {
		Object toInstance = to.type.newInstance();
		MappingContext context = new MappingContext(destinationType: to.type, sourceType: from.type, sourceInstance: instance, filteredProperties:filteredProperties, overrides:overrides);
		to.process(context, instance, toInstance);
		return toInstance;
	}
	
	protected void map(Object instance, Object toInstance) {		
		MappingContext context = new MappingContext(destinationType: to.type, sourceType: from.type, sourceInstance: instance);
		
		to.process(context, instance, toInstance);
	}
	
//	protected Object map(Map<String, Object> fromMap) {
//		Object toInstance = to.type.newInstance();
//		MappingContext context = new MappingContext(destinationType: to.type, sourceType: from.type, sourceInstance: fromMap);
//		to.process(context, fromMap, toInstance);
//		return toInstance;
//	}
}
