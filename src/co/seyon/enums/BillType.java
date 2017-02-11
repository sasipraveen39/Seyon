package co.seyon.enums;

public enum BillType implements Type{
	ADVANCE("Advance"), FINAL("Final");

	private String value;

	private BillType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
