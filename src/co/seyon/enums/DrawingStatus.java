package co.seyon.enums;

public enum DrawingStatus implements Type {
	DRAFT("Draft"),FINAL("Final") ;
	
	private String value;
	
	private DrawingStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
