package co.seyon.enums;

public enum DocumentType implements Type{
	IMAGE("Image"), BILL("Bill"), RECEIPT("Receipt"), CONTRACT("Contract");

	private String value;

	private DocumentType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
