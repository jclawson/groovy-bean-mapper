import com.jasonclawson.groovybeanmapper.v2.BeanMapping;



class V2MappingConfiguration {
	public static BeanMapping setupMapper() {
//		Mapper mapper = Mapping.create(Foo, Bar) {
//			to.name = from.firstName
//		};
		
		BeanMapping.getInstance().createMapping(Foo, Bar) {
			to.name = from.firstName
		};
	
		return BeanMapping.getInstance();
	}
}
