package co.seyon.view.model;

import com.fasterxml.jackson.annotation.JsonView;

public class ProjectSearch {

	@JsonView(Views.Public.class)
	private String projectNumber;
	
	@JsonView(Views.Public.class)
	private String title;
	
	@JsonView(Views.Public.class)
	private String type;
	
	@JsonView(Views.Public.class)
	private String pincode;

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


}
