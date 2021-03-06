package com.jasonclawson.groovybeanmapper

import groovy.transform.EqualsAndHashCode

import com.jasonclawson.groovybeanmapper.converters.StringToUuidConverter



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
		registerConverter(String, UUID, new StringToUuidConverter());
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
		//String when = ""; <-- used if you forget the parenthesis on when()
		
		//public Condition barn;
		
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
		
		def propertyMissing(String name) {
			println("get "+name+" on ConfigHelper");
			return "test";
		}
		
		def when (Object... o) {
			println("when called");
			return new Predicate(o[0]);
		}
		
		
		
//		def isSet () {
//			println("isset called");
//			return "value from isset"
//		}
	}
	
	
	public <F,T> void createMapping(Class<F> fromClass, Class<T> toClass, Closure closure) {
		 def helper = new ConfigHelper(
			from : new SourceProperty<F>(fromClass),
			to :   new DestinationProperty<T>(toClass)
		 );
	 
	 	helper.metaClass.barn = new Condition(){
				boolean check(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
					return false;
				}
			};
		 
	 	 //for whatever reason, this fucks everythign up... just leave it to default
		 //closure.resolveStrategy = Closure.DELEGATE_FIRST;
	
		 
		 closure.setDelegate(helper);
		 closure.call();
		 		 
		 def key = new MappingKey(from:fromClass, to:toClass);
		 
		 mappers.put(key, new ConfigMapper<F,T>(helper.from, helper.to));
	}
	
	public void registerConverter(Class fromClass, Class toClass, Closure converter) {
		def key = new MappingKey(from:fromClass, to:toClass);
		//TODO: warn if key exists	
		mappers.put(key, new ConverterMapper(converter));
	}
	
	private <F,T> Mapper<F,T> getMapper(Class<F> from, Class<T> to) {
		def key = new MappingKey(from:from, to:to);
		return mappers.get(key);
	}
	
	public <F,T> MapperBuilder<F,T> mapper(F from, Class<T> to) {
        return new MapperBuilder<F,T>(getMapper(from.class, to), from);
    }
	
	public <F,T> MapperBuilder<F,T> mapper(F from, T to) {
		return new MapperBuilder<F,T>(getMapper(from.class, to.class), from, to);
	} 
	
	/**
	 * Use this mapper builder if you have a superclass or interface, and an instance that implements it as your target
	 * 
	 * TODO: instead of this, lets build a memoized Mapper finder that will look at all superclasses / super interface combinations
	 * to try and find the mapper
	 * 
	 * @param from    - the from instance in which values will be sourced
	 * @param toClass - the target type
	 * @param to      - the target instance which will receive the mapped values
	 * @return
	 */
	public <F,T> MapperBuilder<F,T> mapper(F from, Class<T> toClass, T to) {
		return new MapperBuilder<F,T>(getMapper(from.class, toClass), from, to);
	}
}
