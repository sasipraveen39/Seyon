package co.seyon.exception;

public class UserDeActiveException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserDeActiveException(String username) {
		super("user : " + username + " is de-actictivated.");
	}
}
