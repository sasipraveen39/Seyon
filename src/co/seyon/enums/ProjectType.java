package co.seyon.enums;

public enum ProjectType implements Type{
	COMMERCIAL("Commercial"), RESEDENTIAL("Resedential");

	private String value;

	private ProjectType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
