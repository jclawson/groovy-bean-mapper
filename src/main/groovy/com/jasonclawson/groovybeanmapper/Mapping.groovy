package com.jasonclawson.groovybeanmapper

import java.util.Map;


import groovy.lang.Closure;
import groovy.transform.EqualsAndHashCode;

class Mapping {
    private Map<MappingKey, Mapper> mappers = new HashMap();
    
    private Mapping(){}
    
    static class SingletonHolder {
        static final Mapping INSTANCE = new Mapping();
    }
    
    public static Mapping getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @EqualsAndHashCode
    class MappingKey {
        Class from
        Class to
    }
    
    protected void registerMapping(Class from, Class to, Mapper mapper) {
        def key = new MappingKey(from:from, to:to);
        mappers.put(key, mapper);
    }
    
    protected Mapper getMapper(Class from, Class to) {
        def key = new MappingKey(from:from, to:to);
        return mappers.get(key);
    }
    
    public <T> T map(Object from, Class<T> to) {
        return getMapper(from.class, to).map(from);
    }
    
    
    static Mapper create(Class fromClass, Class toClass, Closure closure) {
        def obj = [
           from : new MappingProxy(fromClass),
           to :   new MappingProxy(toClass)
        ];
        
        closure.setDelegate(obj);
        closure.call();
        
        Mapper mapper = new Mapper(obj);
        
        Mapping.getInstance().registerMapping(fromClass, toClass, mapper);
        
        return mapper;
    }
}
