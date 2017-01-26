package co.seyon.enums;

public enum DrawingType {
	STRUCTURAL("Structural");

	private String value;

	private DrawingType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
