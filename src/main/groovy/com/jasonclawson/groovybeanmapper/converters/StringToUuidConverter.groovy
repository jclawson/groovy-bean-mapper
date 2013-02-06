package com.jasonclawson.groovybeanmapper.converters


class StringToUuidConverter extends Closure<UUID> {
	public StringToUuidConverter(){
		super(null);
	}
	
	public UUID doCall(Object... args)  throws Exception {
		return UUID.fromString(args[0]);
	}
}
