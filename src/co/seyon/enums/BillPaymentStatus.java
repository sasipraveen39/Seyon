package co.seyon.enums;

public enum BillPaymentStatus implements Type {
	PENDING("Pending"), PAID("Paid"), PAID_IN_EXCESS("Paid In Excess");
	
	private String value;
	
	private BillPaymentStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
