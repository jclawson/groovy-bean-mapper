package com.jasonclawson.groovybeanmapper.v2

interface Condition {
	boolean check(MappingContext context, Object instance, String fromPropertyName, String toPropertyName);
}
