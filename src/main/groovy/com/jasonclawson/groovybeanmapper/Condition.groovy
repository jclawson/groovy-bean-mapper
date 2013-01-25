package com.jasonclawson.groovybeanmapper

interface Condition {
	boolean check(MappingContext context, Object instance, String fromPropertyName, String toPropertyName);
}
