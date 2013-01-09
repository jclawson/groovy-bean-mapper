package com.jasonclawson.groovybeanmapper

import groovy.lang.Closure;

class BeanMapper {
    static Mapper map(Class fromClass, Class toClass, Closure closure) {
//        if(!fromClass instanceof GroovyObject) {
//            
//        }
//        
//        if(!toClass instanceof GroovyObject) {
//            
//        }
//        
        
        
        def obj = [
           from : new MappingProxy(fromClass),
           to :   new MappingProxy(toClass)
        ];
        
        closure.setDelegate(obj);
        closure.call();
        
        Mapper mapper = new Mapper(obj);
        
        MapManager.getInstance().registerMapping(fromClass, toClass, mapper);
        
        return mapper;
    }
}
