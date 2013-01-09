package com.jasonclawson.groovybeanmapper



class MappingProxyTest extends GroovyTestCase {
	class Foo {
		UUID name
        boolean isSet;
        FooSub sub
	}
	
	class Bar {
		String name2
        BarSub barSub;
	}
    
    class FooSub {
        int zip
        String street
    }
    
    class BarSub {
        String zipCode
        String street
    }
	
    //checks the isSet variable to determine whether to make the mapping or not
    def isSet(closure) {
        closure.validators.add({a, b ->
            return b.isSet;
        });
        return closure;
    }
	
	void testSomething() {
        Mapper mapper = Mapping.create(Foo, Bar) {
            isSet to.name2 = from.name
            to.barSub = from.sub
        };
    
        Mapping.create(FooSub, BarSub) {
            to.zipCode = from.zip
            to.street = from.street  
        };
    
        Foo foo = new Foo();
        
        FooSub sub = new FooSub(zip:80222, street:"Vassar");
        foo.sub = sub;
        foo.name = UUID.randomUUID();
        foo.isSet = true;
        
        Bar bar = mapper.map(foo);
        
        //def barJson = Converter.toStr
        
        println("Mapped name Foo -> Bar: "+bar.dump());
	}
}