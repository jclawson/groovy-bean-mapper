package com.jasonclawson.groovybeanmapper.v2import java.util.List;
import java.util.Map;



interface Mapping<F,T> {
	public T map(F instance);
	public T mapFiltered(F instance, List<String> includeProperties, Map<String, Object> overrides);	public T map(F instance, T toInstance);
}
