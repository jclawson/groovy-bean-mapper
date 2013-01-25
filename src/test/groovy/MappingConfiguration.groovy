import com.jasonclawson.groovybeanmapper.BeanMapping;



class MappingConfiguration {
    public static BeanMapping getMapper() {
		BeanMapping mapping = BeanMapping.getInstance();
		
		mapping.createMapping(Foo, Bar) {
            to.name = from.firstName
        };
    
        return mapping;
    }
}
