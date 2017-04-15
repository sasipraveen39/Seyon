package co.seyon.enums;

public enum PaymentStatus implements Type {
	NOT_PAID("Not Paid"), PAID("Paid");
	
	private String value;
	
	private PaymentStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
