package com.jasonclawson.groovybeanmapper

import test.PersonBean;



class MappingProxyTest extends GroovyTestCase {
	class Foo {
		UUID name
        boolean isSet;
        FooSub sub
	}
	
	class Bar {
		String name2
        BarSub barSub;
		String zip
	}
    
    class FooSub {
        int zip
        String street
    }
    
    class BarSub {
        String zipCode
        String street
    }
	
	class Person {
		String name
		long age
	}
	
    //checks the isSet variable to determine whether to make the mapping or not
    def isSet(closure) {
        closure.validators.add({a, b, name, type ->
            return b.isSet;
        });
        return closure;
    }
	
	def newIsSet(closure) {
		closure.validators.add({a, b, name, type ->
			Class c = b.getClass();
			def m = c.getMethod("getField", String.class);
			return b.isFieldSet(m.invoke(null, name))
		});
		return closure;
	}
	
	void testSomething() {

		
		
		
		Mapper mapper = Mapping.create(Foo, Bar) {
            isSet to.name2 = from.name
            to.barSub = from.sub
			to.zip = from.sub.zip
        };
    
        Mapping.create(FooSub, BarSub) {
            to.zipCode = from.zip
            to.street = from.street  
        };
	
		Mapping.create(PersonBean, Person) {
			newIsSet to.name = from.name
			newIsSet to.age = from.age
		};
	
//		PersonBean bean = new PersonBean();
//		bean.age = 29;
//		bean.name = "Jason";
//    
//		Person p = Mapping.getInstance().map(bean, Person);
//		
//		println(p.dump());
		
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