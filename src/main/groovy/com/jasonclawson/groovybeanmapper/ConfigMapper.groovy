package com.jasonclawson.groovybeanmapper


import org.slf4j.LoggerFactory;

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class ConfigMapper<F,T> extends Mapper<F,T> {
	private SourceProperty<F> from;
	private DestinationProperty<T> to;
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(ConfigMapper);
	
	public ConfigMapper(SourceProperty<F> from, DestinationProperty<T> to) {
		this.from = from;
		this.to = to;
	}
	
	public T map(F instance, MappingContext context) {
		Object toInstance = to.type.newInstance();
		map(instance, toInstance, context);
		return toInstance;
	}
	
	public T map(F instance, T toInstance, MappingContext context) {
		//destination has the setters (the mapping bindings)
		to.setters.each() {propertyName, propertyMapping ->
			String propertyPath = context.getPropertyPath(propertyName);
			if(!context.isSkip(propertyPath) && !propertyMapping.isSkip(context, instance, propertyMapping._$getName(), propertyName)) {
				Class toType = FieldUtils.getInheritedDeclaredField(propertyName, toInstance.class)?.type;				
				Object value = propertyMapping.get(context, instance);
					
				//what should we do if the value is null?  ignore the mapping... set it?
				if(value == null) {
					if(toType.isPrimitive()) {
						log.warn("Cannot set {} to null.  Its a primitive.", toType);
						return;
					}					
					
					toInstance[propertyName] = null;
					return;
				}
							
				if(toType == String || toType.isAssignableFrom(value.getClass())) {
					toInstance[propertyName] = value;
				} else if (toType.isPrimitive() || value.getClass().isPrimitive()) {
					//lets try just making the assignment relying on autoboxing
					try {
						toInstance[propertyName] = value;
					} catch(RuntimeException e) {
						log.error("Tried to make a direct assignment of a primitive type relying on autoboxing but it failed {} -> {}",toType, value.class);
						throw e;
					}
				} else {
					//the types are not compatible... we need to map them!
					Mapper mapper = BeanMapping.getInstance().getMapper(value.class, toType);
					if(mapper == null) {
						log.warn("No Mapper for {} to {}",value.class,toType);
						return;
					}
					value = mapper.map(value, context);
					toInstance[propertyName] = value;
				}
			} else {
				log.debug("skipped {}", propertyName);
			}
		};
	}
}
