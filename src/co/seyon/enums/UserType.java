package co.seyon.enums;

public enum UserType {
	ADMIN("Admin"), CLIENT("Client"), VENDOR("Vendor");
	
	private String value;
	
	private UserType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
