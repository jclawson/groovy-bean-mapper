package com.jasonclawson.groovybeanmapper

class Mapper {
    MappingProxy from;
    MappingProxy to;
    
    public Mapper(Object o) {
        this.from = o.from;
        this.to = o.to;
    }
    
    def map(Object instance) {
        Object toInstance = to.type.newInstance();
        
        MappingContext context = new MappingContext(destinationType: to.type, sourceType: from.type, sourceInstance: instance);
        
        //working on syntax to apply mappings
        to.process(context, instance, toInstance);
        
        return toInstance;
    }
}
