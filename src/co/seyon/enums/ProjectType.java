package co.seyon.enums;

public enum ProjectType implements Type{
	RESEDENTIAL("Resedential"), COMMERCIAL("Commercial"), EDUCATIONAL("Educational");

	private String value;

	private ProjectType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
