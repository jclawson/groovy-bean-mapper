package com.jasonclawson.groovybeanmapperimport java.util.List;
import java.util.Map;



abstract class Mapper<F,T> {
			public abstract T map(F instance, MappingContext context);	public abstract T map(F instance, T toInstance, MappingContext context);		
}
