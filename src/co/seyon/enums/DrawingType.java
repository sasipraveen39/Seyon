package co.seyon.enums;

public enum DrawingType implements Type{
	ARCHITECTURAL("Architectural"), ELECTRICAL("Electrical"), 
	INTERIOR("Interior"), PLUMBING("Plumbing"), STRUCTURAL("Structural");

	private String value;

	private DrawingType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
