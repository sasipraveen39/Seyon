package co.seyon.enums;

public enum HistoryType {
	ADD("Add"), DELETE("Delete"), UPDATE("Update");

	private String value;

	private HistoryType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
