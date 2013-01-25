package com.jasonclawson.groovybeanmapper

class Predicate {
	
	Condition[] args;
	
	public Predicate(Condition... args) {
		this.args = args;
	}
	
	def and(Object... args) {
		throw new RuntimeException("Sorry 'and' is not supported yet!");
	}
	
	def or(Object... args) {
		throw new RuntimeException("Sorry 'or' is not supported yet!");
	}
	
	//called from SourceProperty isSkip
	boolean check(MappingContext context, Object instance, String fromPropertyName, String toPropertyName) {
		if(args != null) {
			for(int i=0; i<args.length; i++) {
				if(!args[i].check(context, instance, fromPropertyName, toPropertyName)) {
					return false;
				}
			}
		}
		return true;
	}
}

/*
def when(Object... args) {
    return new Object(){
       def and(Object... args2) {
       
       }
    };
}

def isSet = "instance";

 when isSet, isSet and isSet, isSet

*/