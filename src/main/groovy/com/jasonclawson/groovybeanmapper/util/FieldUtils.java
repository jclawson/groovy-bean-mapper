package com.jasonclawson.groovybeanmapper.util;


import java.lang.reflect.Field;

public class FieldUtils {
		
	
	public static Field getInheritedDeclaredField(String name, Class<?> cls) throws NoSuchFieldException {
		Class<?> currentCls = cls;
		while(currentCls != null) {		
			try {
				Field f = currentCls.getDeclaredField(name);
				return f;
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {}
			currentCls = currentCls.getSuperclass();
		}
		throw new NoSuchFieldException("Field "+name+" not found on class "+cls.getCanonicalName()+" or its superclasses.");
	}
}
