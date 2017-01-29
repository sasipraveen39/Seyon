package co.seyon.view.model;

import com.fasterxml.jackson.annotation.JsonView;

public class AccountSearchResult extends SearchResult {
	@JsonView(Views.Public.class)
	private String accountNumber;
	
	@JsonView(Views.Public.class)
	private String accountName;
	
	@JsonView(Views.Public.class)
	private String mobileNumber;
	
	@JsonView(Views.Public.class)
	private String email;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
