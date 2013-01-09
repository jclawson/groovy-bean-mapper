import com.jasonclawson.groovybeanmapper.Mapping
import com.jasonclawson.groovybeanmapper.Mapper



class MappingConfiguration {
    public static Mapper getMapper() {
        Mapper mapper = Mapping.create(Foo, Bar) {
            to.name = from.firstName
        };
    
        return mapper;
    }
}
