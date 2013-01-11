package com.jasonclawson.groovybeanmapper

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
	
	protected void map(Object instance, Object toInstance) {		
		MappingContext context = new MappingContext(destinationType: to.type, sourceType: from.type, sourceInstance: instance);
		
		to.process(context, instance, toInstance);
	}
}
