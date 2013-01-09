import com.jasonclawson.groovybeanmapper.BeanMapper
import com.jasonclawson.groovybeanmapper.Mapper



class MappingConfiguration {
    public static Mapper getMapper() {
        Mapper mapper = BeanMapper.map(Foo, Bar) {
            to.name = from.firstName
        };
    
        return mapper;
    }
}
