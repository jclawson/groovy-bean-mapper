import org.junit.Test;

import com.jasonclawson.groovybeanmapper.Mapper;
import com.jasonclawson.groovybeanmapper.Mapping;


public class JavaMappingTest {
    
    @Test
    public void testMapping() {
        Mapper mapper = MappingConfiguration.getMapper();
        
        Foo foo = new Foo();
        foo.firstName = "Jenny";
        
        Bar bar = (Bar) Mapping.getInstance().map(foo,Bar.class);
        
        System.out.println("Final mapping: "+bar.name);
        
    }
}
