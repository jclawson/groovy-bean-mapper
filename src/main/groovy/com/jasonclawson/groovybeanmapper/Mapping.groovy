package com.jasonclawson.groovybeanmapper

import groovy.transform.EqualsAndHashCode

class Mapping {
    private Map<MappingKey, Mapper> mappers = new HashMap();
    
    private Mapping(){
		
		createAndRegisterConverter(String, UUID, { instance ->
			return UUID.fromString(instance);
		});
	}
    
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
	
	public void map(Object from, Object to) {
		getMapper(from.class, to.class).map(from, to);
	}
	
	/**
	 * 
	 * @param filteredProperties - list of mappings you want applied (these should be in from) and only work on the first level
	 * @param from
	 * @param to
	 * @return
	 */
	public <T> T map(List<String> filteredProperties, Map<String, Object> overrides, Object from, Class<T> to) {
		//copy from "Map" to a new from "Instance" 
		getMapper(from.class, to).mapFiltered(from, filteredProperties, overrides);
	}
    
	private void createAndRegisterConverter(Class fromClass, Class toClass, Closure closure) {		 
		 Mapper mapper = new Mapper([to:null, from:null]){
			 protected Object map(Object instance) {
				 return closure.call(instance);
			 }
		 };		 
		 registerMapping(fromClass, toClass, mapper);
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
