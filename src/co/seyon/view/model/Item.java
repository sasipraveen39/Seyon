package co.seyon.view.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonView;

public class Item {
	@JsonView(Views.Public.class)
	private String type;
	
	@JsonView(Views.Public.class)
	private ArrayList<Long> ids;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Long> getIds() {
		return ids;
	}

	public void setIds(ArrayList<Long> ids) {
		this.ids = ids;
	}
	
	
}
