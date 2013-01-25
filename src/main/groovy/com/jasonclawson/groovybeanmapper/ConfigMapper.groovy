package com.jasonclawson.groovybeanmapper

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class ConfigMapper<F,T> extends Mapper<F,T> {
	private SourceProperty<F> from;
	private DestinationProperty<T> to;
	
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
			if(!context.isSkip(propertyPath) && !propertyMapping.isSkip(context, instance, propertyMapping.name, propertyName)) {
				Class toType = FieldUtils.getInheritedDeclaredField(propertyName, toInstance.class)?.type;				
				Object value = propertyMapping.get(context, instance);
					
				//what should we do if the value is null?  ignore the mapping... set it?
				if(value == null) {
					toInstance[propertyName] = null;
					return;
				}
							
				if(toType == String || toType.isAssignableFrom(value.class)) {
					toInstance[propertyName] = value;
				} else {
					//the types are not compatible... we need to map them!
					Mapper mapper = BeanMapping.getInstance().getMapper(value.class, toType);
					if(mapper == null) {
						println("No Mapper for "+value.class.simpleName+" to "+toType.simpleName);
						return;
					}
					value = mapper.map(value, context);
					toInstance[propertyName] = value;
				}
			} else {
				println("skipped "+propertyName);
			}
		};
	}
}
