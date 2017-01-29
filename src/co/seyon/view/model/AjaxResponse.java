package co.seyon.view.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;	

public class AjaxResponse {

	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;
	
	@JsonView(Views.Public.class)
	List<? extends SearchResult> result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<? extends SearchResult> getResult() {
		return result;
	}

	public void setResult(List<? extends SearchResult> result) {
		this.result = result;
	}
	
}
