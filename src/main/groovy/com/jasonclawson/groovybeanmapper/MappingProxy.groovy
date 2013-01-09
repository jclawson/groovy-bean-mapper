package com.jasonclawson.groovybeanmapper

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
        def propType =  type.declaredFields.find {it.name == name }.type;
		return new GetterProxy(name, type, propType);
	}
	
    //setter prop missing
	def propertyMissing(String name, value) {
		println("set "+name+" to "+value+" on "+type.getSimpleName());
        setters[name] = value;
	}
    
    
    
    def process(MappingContext context, Object fromInstance, Object toInstance) {
        println("MappingProxy process fromInstance -> toInstance");
        assert(toInstance.class == type);
        setters.each() {key, value -> 
            if(value.shouldMap(context, fromInstance)) {
                //
                //FIXME: check if the types are compatible... if not we need to lookup a mapper for 
                //these types
                
                Class toType = toInstance.class.declaredFields.find {it.name == key }.type;
                
                if(toType != String && value.type != toType) {
                    Mapper mapper = Mapping.getInstance().getMapper(value.type, toType);
                    if(mapper == null) {
                        println("No mapper for "+value.type.getSimpleName()+" to "+toType.getSimpleName());
                    } else {
                        toInstance[key] = mapper.map(value.get(context, fromInstance));
                    }
                    
                } else {
                    toInstance[key] = value.get(context, fromInstance);
                }
            }
        }
    }
}
