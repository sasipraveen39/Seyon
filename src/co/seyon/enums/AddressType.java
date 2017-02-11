package co.seyon.enums;

public enum AddressType implements Type {
	BILLING("Billing"), PROJECT_SITE("Project Site");
	
	private String value;
	
	private AddressType(String value){
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
}
