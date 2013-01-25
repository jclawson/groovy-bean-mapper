

package com.jasonclawson.groovybeanmapper

import groovy.transform.ToString;

import com.jasonclawson.groovybeanmapper.v2.BeanMapping
import com.jasonclawson.groovybeanmapper.v2.MapperBuilder

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
	
//	Closure isSet() {
//		return { arg1, arg2 ->
//			return true;
//		};
//	}
	
	void testSomething() {
		BeanMapping.getInstance().createMapping(Foo, Bar) {
			to.uuidString = from.uuid when isSet, hasMany
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
		Bar bar = builder.map(foo);
		
		println(bar);
	}
}
