package co.seyon.enums;

public enum HistoryType implements Type{
	ADD("Add"), DELETE("Delete"), UPDATE("Update");

	private String value;

	private HistoryType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
