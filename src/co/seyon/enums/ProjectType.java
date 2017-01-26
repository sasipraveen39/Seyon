package co.seyon.enums;

public enum ProjectType {
	RESEDENTIAL("Resedential"), COMMERCIAL("Commercial"), EDUCATIONAL("Educational");

	private String value;

	private ProjectType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
