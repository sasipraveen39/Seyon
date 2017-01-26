package co.seyon.enums;

public enum BillType {
	ADVANCE("Advance"), FINAL("Final");

	private String value;

	private BillType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
