package com.jasonclawson.groovybeanmapper.v2

import groovy.transform.EqualsAndHashCode




class BeanMapping {
	
	private Map<MappingKey, Mapper> mappers = new HashMap();
	
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
	
//	public static Object runtime(String key) {
//		
//	}
	
	protected class ConfigHelper<F,T> {
		SourceProperty<F> from
		DestinationProperty<T> to
		
		def runtime(String key) {
			return new RuntimeProperty(key);
		}
		
//		def when (Closure... conditions) {			
//			return { MappingContext context, Object instance ->
//				for(int i=0; i<conditions.length; i++) {
//					if(!conditions[i].call(context, instance))
//						return false;
//				}
//				return true;
//			};
//		}
		
		def when (Object o) {
			println("when called");
		}
		
		def isSet () {
			println("isset called");
			return "value from isset"
		}
	}
	
	public <F,T> void createMapping(Class<F> fromClass, Class<T> toClass, Closure closure) {
		 def helper = new ConfigHelper(
			from : new SourceProperty<F>(fromClass),
			to :   new DestinationProperty<T>(toClass),
		 );
		 
	 	 closure.resolveStrategy = Closure.DELEGATE_FIRST;
		 closure.setDelegate(helper);
		 closure.call();
		 		 
		 def key = new MappingKey(from:fromClass, to:toClass);
		 
		 mappers.put(key, new ConfigMapper<F,T>(helper.from, helper.to));
	}
	
	public void createConverter(Class fromClass, Class toClass, Closure converter) {
		def key = new MappingKey(from:fromClass, to:toClass);
		//TODO: warn if key exists	
		mappers.put(key, new ConverterMapper(converter));
	}
	
	private <F,T> Mapper<F,T> getMapper(Class<F> from, Class<T> to) {
		def key = new MappingKey(from:from, to:to);
		return mappers.get(key);
	}
	
	public <F,T> MapperBuilder<F,T> mapper(F from, Class<T> to) {
        return new MapperBuilder<F,T>(getMapper(from.class, to));
    }
	
	public <F,T> MapperBuilder<F,T> mapper(F from, T to) {
		return new MapperBuilder<F,T>(getMapper(from.class, to.class));
	} 
}
