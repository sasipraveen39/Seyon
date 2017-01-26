package co.seyon.enums;

public enum AddressType {
	BILLING("Billing"), PROJECT_SITE("Project Site");
	
	private String value;
	
	private AddressType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
