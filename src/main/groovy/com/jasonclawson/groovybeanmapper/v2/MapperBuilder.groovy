package com.jasonclawson.groovybeanmapper.v2;

import java.util.List;
import java.util.Map;


public class MapperBuilder<F,T> {
	Map<String, Object> properties;
	List<String> includeOnly;
	
	Mapper<F, T> mapper;
	
	public MapperBuilder(Mapper<F, T> mapper) {
		this.mapper = mapper;
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

	public Object map(Object instance) {
		MappingContext context = new MappingContext(includeMapOnly: includeOnly, runtimeValues:properties);		
		return mapper.map(instance, context);
	}

	public Object map(Object instance, Object toInstance) {
		MappingContext context = new MappingContext(includeMapOnly: includeOnly, runtimeValues:properties);		
		return mapper.map(instance, toInstance, context);
	}
}