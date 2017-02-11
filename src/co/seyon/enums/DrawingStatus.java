package co.seyon.enums;

public enum DrawingStatus implements Type {
	FINAL("Final"), DRAFT("Draft");
	
	private String value;
	
	private DrawingStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
