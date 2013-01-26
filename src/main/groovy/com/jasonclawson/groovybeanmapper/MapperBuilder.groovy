package com.jasonclawson.groovybeanmapper;

import java.util.List;
import java.util.Map;


public class MapperBuilder<F,T> {
	Map<String, Object> properties;
	List<String> includeOnly;
	
	Mapper<F, T> mapper;
	F fromInstance;
	T toInstance;
	
	public MapperBuilder(Mapper<F, T> mapper, F fromInstance) {
		this.mapper = mapper;
		this.fromInstance = fromInstance;
	}
	
	public MapperBuilder(Mapper<F, T> mapper, F fromInstance, T toInstance) {
		this(mapper, fromInstance);
		this.toInstance = toInstance;
	}
	
	/**
	 * When properties are mapped as runtime(key) this method allows you to set the 
	 * value they will recieve.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	MapperBuilder<F,T> withProperty(String name, Object value) {
		if(properties == null) {
			properties = new HashMap();
		}		
		properties.put(name, value);
		return this;
	}
	
	/**
	 * Only map the following mappings (keyed on destination)
	 * @param includeOnly
	 * @return
	 */
	MapperBuilder<F,T> includeOnly(List<String> includeOnly) {
		this.includeOnly = includeOnly;
		return this;
	}	

	public T map() {
		MappingContext context = new MappingContext(includeMapOnly: includeOnly, runtimeValues:properties);		
		if(toInstance == null)		
			return mapper.map(fromInstance, context);
		else
			return mapper.map(fromInstance, toInstance, context);
	}

//	public T map(Object toInstance) {
//		MappingContext context = new MappingContext(includeMapOnly: includeOnly, runtimeValues:properties);		
//		return mapper.map(fromInstance, toInstance, context);
//	}
}