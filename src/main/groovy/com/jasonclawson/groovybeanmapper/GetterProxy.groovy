package com.jasonclawson.groovybeanmapper

class GetterProxy {
    Class<?> type;
    String name;
    Class<?> ownerType;
    
    def setters = new HashMap();
    
    def validators = new ArrayList();
    
    
    GetterProxy(String name, Class<?> ownerType, Class<?> type) {
        this.name = name;
        this.ownerType = ownerType;
        this.type = type;
        println("GetterProxy() for "+type.getSimpleName()+" ("+name+" on "+ownerType.getSimpleName()+")")
    }
    
    def propertyMissing(String name) {
        println("get nested "+name+" on "+type.getSimpleName());
        throw new RuntimeException("nested properties not supported yet");
    }
    
    boolean shouldMap(MappingContext context, Object instance) {
        for(int i =0; i<validators.size(); i++) {
            if(!validators[i].call(context, instance)) {
                return false;
            }
        }
        return true;
    }
    
    def get(MappingContext context, Object instance){
//        if(type == String) {
//            return instance;
//        }
        
        //I think we will always return instance on the tail end
        println("Instance: "+instance);
        return instance[name];
    }
}
