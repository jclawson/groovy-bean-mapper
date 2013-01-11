
package test;

public class PersonBean {
	
	private String name;
	private long age;
	
	private boolean isAgeSet;
	private boolean isNameSet;
	
	public enum Field {
		NAME,
		AGE
	}
	
	public static Field getField(String name) {
		if(name.equals("name")) {
			return Field.NAME;
		} else {
			return Field.AGE;
		}
	}
	
	public boolean isFieldSet(Field field) {
		switch(field) {
		  case NAME:
			 return isNameSet;
		  default:
				 return isAgeSet;
		}
		
	}
	
	public void setName(String name) {
		this.name = name;
		this.isNameSet = true;
	}
	
	public void setAge(long age) {
		this.age = age;
		this.isAgeSet = true;
	}
	
	public String getName() {
		return name;
	}
	
	public long getAge() {
		return age;
	}
}
