import org.junit.Test;

import com.jasonclawson.groovybeanmapper.v2.BeanMapping;


public class JavaMappingTest {
    
    @Test
    public void testMapping() {
        BeanMapping mapper = MappingConfiguration.getMapper();
        
        Foo foo = new Foo();
        foo.firstName = "Jenny";
        
        Bar bar = (Bar) mapper.mapper(foo,Bar.class).map(foo);
        
        System.out.println("Final mapping: "+bar.name);
        
    }
}
