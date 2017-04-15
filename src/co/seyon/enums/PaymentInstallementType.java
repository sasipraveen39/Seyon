package co.seyon.enums;

public enum PaymentInstallementType implements Type{
	ONE_PART("One Part"), TWO_PART("Two Part"), THREE_PART("Three Part");

	private String value;

	private PaymentInstallementType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
