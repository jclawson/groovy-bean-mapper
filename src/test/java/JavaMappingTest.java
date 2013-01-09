import org.junit.Test;

import com.jasonclawson.groovybeanmapper.Mapper;


public class JavaMappingTest {
    
    @Test
    public void testMapping() {
        Mapper mapper = MappingConfiguration.getMapper();
        
        Foo foo = new Foo();
        foo.firstName = "Jenny";
        
        Bar bar = (Bar) mapper.map(foo);
        
        System.out.println("Final mapping: "+bar.name);
        
    }
}
