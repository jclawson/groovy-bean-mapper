import org.junit.Test;

import com.jasonclawson.groovybeanmapper.BeanMapping;


public class JavaMappingTest {
    
    @Test
    public void testMapping() {
        BeanMapping mapper = MappingConfiguration.getMapper();
        
        Foo foo = new Foo();
        foo.firstName = "Jenny";
        
        Bar bar = (Bar) mapper.mapper(foo,Bar.class).map();
        
        System.out.println("Final mapping: "+bar.name);
        
    }
}
