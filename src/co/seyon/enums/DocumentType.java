package co.seyon.enums;

public enum DocumentType {
	IMAGE("Image"), BILL("Bill"), RECEIPT("Receipt"), CONTRACT("Contract");

	private String value;

	private DocumentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
