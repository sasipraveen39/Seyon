package co.seyon.enums;

public enum BillStatus implements Type {
	DRAFT("Draft"),FINAL("Final") ;
	
	private String value;
	
	private BillStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
