package co.seyon.enums;

public enum UserType implements Type{
	ADMIN("Admin"), CLIENT("Client"), VENDOR("Vendor");
	
	private String value;
	
	private UserType(String value){
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
}
