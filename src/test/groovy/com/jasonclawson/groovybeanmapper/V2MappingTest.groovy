package com.jasonclawson.groovybeanmapper

import groovy.transform.ToString


class V2MappingTest extends GroovyTestCase {
	@ToString
	class Foo {
		UUID uuid
		String zip
		FooAddress address
	}
	
	@ToString
	class FooAddress {
		String street
	}
	
	@ToString
	class Bar {
		String uuidString
		BarAddress address;
		String zipCode
		String something
	}
	
	@ToString
	class BarAddress {
		String street1
	}
	
	Condition isSet = new Condition(){
		boolean check(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
			println(context.getPropertyPath(toPropertyName)+" = "+fromPropertyName+": "+instance.class);
			return false;
		}		
	};
	
	
	
	void testSomething() {
		BeanMapping.getInstance().createMapping(Foo, Bar) {
			to.uuidString = from.uuid when(isSet);
			to.zipCode    = from.zip
			to.address    = from.address
			to.something  = runtime("abc")
		};
	
		BeanMapping.getInstance().createMapping(FooAddress, BarAddress) {
			to.street1 = from.street
		};
	
		Foo foo = new Foo(uuid: UUID.randomUUID(), zip: "80222", address : new FooAddress(street:"Vassar"));
		MapperBuilder builder = BeanMapping.getInstance().mapper(foo, Bar);
			builder.withProperty("abc", "Jason");
		
		Bar bar = builder.map();
		
		println(bar);
	}
	
}
