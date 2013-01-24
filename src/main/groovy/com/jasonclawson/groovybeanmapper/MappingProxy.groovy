package com.jasonclawson.groovybeanmapper

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class MappingProxy {
	Class<?> type;
    
    def setters = new HashMap();
    
	MappingProxy(Class<?> type) {
		this.type = type;
		println("Proxy() for "+type.getSimpleName())
	}
	
	//TODO: make work for private fields
    //getter prop missing
	def propertyMissing(String name) {
		println("get "+name+" on "+type.getSimpleName());
		Field f = FieldUtils.getInheritedDeclaredField(name, type);
        def propType =  f.type;
		return new GetterProxy(name, type, propType);
	}
	
    //setter prop missing
	def propertyMissing(String name, value) {
		println("set "+name+" to "+value+" on "+type.getSimpleName());
        setters[name] = value;
	}
    
    
    
    def process(MappingContext context, Object fromInstance, Object toInstance) {
        //println("MappingProxy process fromInstance -> toInstance");
        assert(toInstance.class == type);
        setters.each() {key, value -> 
            if(context.isFiltered(key) && value.shouldMap(context, fromInstance)) {
                //
                //FIXME: check if the types are compatible... if not we need to lookup a mapper for 
                //these types
                
                //Class toType = toInstance.class.declaredFields.find {it.name == key }?.type;
				Class toType = FieldUtils.getInheritedDeclaredField(key, toInstance.class)?.type;				
				
				if(toType == null) {
					println("ERROR: could not find "+key+" on "+toInstance.class.simpleName);
					return;
				}
                
				Object mappedValue = null;
				
                if(toType != String && value.type != toType) {
                    Mapper mapper = Mapping.getInstance().getMapper(value.type, toType);
                    if(mapper == null) {
                        println("No mapper for "+value.type.getSimpleName()+" to "+toType.getSimpleName());
						return;
                    } else {
                    	def fetchedValue = value.get(context, fromInstance);
						if(fetchedValue != null)  
							mappedValue = mapper.map(fetchedValue);
						else
							mappedValue = null; //FIXME: this may not be allowed to be set to null
                    }
                    
                } else {
                    mappedValue = value.get(context, fromInstance);
                }
				
				toInstance[key] = context.getValue(key, mappedValue);
            }
        }
    }
}
