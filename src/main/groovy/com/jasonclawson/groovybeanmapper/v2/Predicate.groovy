package com.jasonclawson.groovybeanmapper.v2

class Predicate {
	
	public Predicate(Condition... args) {
		//condition(s)
	}
	
	def and(Object... args) {
		throw new RuntimeException("Sorry not supported yet!");
	}
}
