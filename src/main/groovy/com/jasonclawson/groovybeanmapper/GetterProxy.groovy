package com.jasonclawson.groovybeanmapper

import java.lang.reflect.Field

import com.jasonclawson.groovybeanmapper.util.FieldUtils

class GetterProxy {
    Class<?> type;
    String name;
    Class<?> ownerType;
    
    def setters = new HashMap();    
    def validators = new ArrayList();
	
	def parentProxy = null;
    
    
    GetterProxy(String name, Class<?> ownerType, Class<?> type) {
        this.name = name;
        this.ownerType = ownerType;
        this.type = type;
        println("GetterProxy() for "+type.getSimpleName()+" ("+name+" on "+ownerType.getSimpleName()+")")
    }
	
	GetterProxy(String name, Class<?> ownerType, Class<?> type, Object parentProxy) {
		this(name, ownerType, type);
		this.parentProxy = parentProxy;
	}
    
    def propertyMissing(String name) {
        println("get nested "+name+" on "+type.getSimpleName());
        //throw new RuntimeException("nested properties not supported yet");
		
		Field nestedField = FieldUtils.getInheritedDeclaredField(name, type);
		
		return new GetterProxy(name, type, nestedField.type, this);
    }
    
    boolean shouldMap(MappingContext context, Object instance) {
        for(int i =0; i<validators.size(); i++) {
            if(!validators[i].call(context, instance, name, type)) {
                return false;
            }
        }
        return true;
    }
    
    def get(MappingContext context, Object instance){
		
		if(parentProxy != null) {
			instance = parentProxy.get(context, instance);
		}
		
		
//        if(type == String) {
//            return instance;
//        }
        
        //I think we will always return instance on the tail end
        println("Instance: "+instance);
        return instance[name];
    }
}
