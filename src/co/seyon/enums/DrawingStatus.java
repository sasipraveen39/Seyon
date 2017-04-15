package co.seyon.enums;

public enum DrawingStatus implements Type {
	DRAFT("Drafting"),DRAFT_COPY_FOR_APPROVAL("Draft Copy For Approval"),FINAL("Final") ;
	
	private String value;
	
	private DrawingStatus(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
