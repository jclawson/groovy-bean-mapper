package com.jasonclawson.groovybeanmapper


class MappingProxyTest extends GroovyTestCase {
	class Foo {
		String name
	}
	
	class Bar {
		String name2
	}
	
	
	void testSomething() {
		//def foo = new Foo();
		def foo = new MappingProxy(Foo);
		def bar = new MappingProxy(Bar);
		foo.name = bar.name2;
	}
}