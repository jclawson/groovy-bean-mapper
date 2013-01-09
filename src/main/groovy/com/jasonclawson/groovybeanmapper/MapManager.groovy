package com.jasonclawson.groovybeanmapper

import groovy.transform.EqualsAndHashCode;

class MapManager { 
    private Map<MappingKey, Mapper> mappers = new HashMap();
    
    private MapManager(){}
    
    static class SingletonHolder {
        static final MapManager INSTANCE = new MapManager();
    }
    
    public static MapManager getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    @EqualsAndHashCode
    class MappingKey {
        Class from
        Class to    
    }
    
    public void registerMapping(Class from, Class to, Mapper mapper) {
        def key = new MappingKey(from:from, to:to);
        mappers.put(key, mapper);
    }
    
    public Mapper getMapper(Class from, Class to) {
        def key = new MappingKey(from:from, to:to);
        return mappers.get(key);
    }
    
    public <T> T map(Object from, Class<T> to) {
        return getMapper(from.class, to).map(from);
    }
    
}
