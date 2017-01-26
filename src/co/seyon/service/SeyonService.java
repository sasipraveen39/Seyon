package co.seyon.service;

import java.text.SimpleDateFormat;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Login;
import co.seyon.model.User;
import co.seyon.util.Constants;
import co.seyon.util.DateUtil;
import co.seyon.util.EncryptionUtil;

public class SeyonService {

	private SimpleDateFormat dateFormat;
	private Finder finder;

	public SeyonService() {
		dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		finder = new Finder();
	}

	public Login getNewLogin() {
		Login login = new Login();
		User newUser = new User();
		login.setUser(newUser);
		return login;
	}

	public User login(String username, String password)
			throws UserDeActiveException, InitialPasswordException {
		User loggedIn = null;
		Login login = finder.findByLoginUserName(username);
		if (login != null) {
			if (!login.isActiveAndNotExpired()) {
				throw new UserDeActiveException(login.getUsername());
			}
			if (isInitialPassword(login.getPassword())) {
				throw new InitialPasswordException();
			}
			if (isPasswordMatch(login, password)) {
				login.setLastLogin(login.getCurrentLogin());
				login.setCurrentLogin(DateUtil.getCurrentTimestamp());
				login.setLoggedIn(true);
				updateLogin(login);
				loggedIn = login.getUser();
			}
		}
		return loggedIn;
	}

	public void refreshData(Object data) {
		Bundle bundle = new Bundle();
		bundle.refresh(data);
		bundle.closeConnection();
	}

	public boolean logOut(Login login) {
		boolean result = false;

		if (login.getLoggedIn()) {
			login.setLoggedIn(false);
			updateLogin(login);
			result = true;
		}

		return result;
	}

	public boolean deactivateLogin(String username) {
		boolean result = false;

		Login login = finder.findByLoginUserName(username);
		if (login.getActive()) {
			login.setActive(false);
			updateLogin(login);
			result = true;
		}

		return result;
	}

	public Login findByUsername(String username) {
		Login login = finder.findByLoginUserName(username);
		return login;
	}

	public boolean activateLogin(String username) {
		boolean result = false;

		Login login = finder.findByLoginUserName(username);
		if (!login.getActive()) {
			login.setActive(true);
			updateLogin(login);
			result = true;
		}

		return result;
	}

	public boolean isPasswordMatch(Login login, String password) {
		return login.getPassword().equals(
				EncryptionUtil.getSecurePassword(password));
	}

	public boolean isInitialPassword(String password) {
		return EncryptionUtil.getSecurePassword(Constants.RESET_PASSWORD)
				.equals(password);
	}

	public boolean updatePassword(String username, String password) {
		boolean result = false;
		Bundle bundle = new Bundle();

		Login login = finder.findByLoginUserName(username);
		if (login != null) {
			login.setPassword(EncryptionUtil.getSecurePassword(password));
			bundle.update(login);
			result = true;
		}

		bundle.closeConnection();
		return result;
	}

	public boolean resetpassword(String username) {
		boolean result = false;
		Bundle bundle = new Bundle();

		Login login = finder.findByLoginUserName(username);
		if (login != null) {
			login.setPassword(EncryptionUtil
					.getSecurePassword(Constants.RESET_PASSWORD));
			bundle.update(login);
			result = true;
		}

		bundle.closeConnection();
		return result;
	}

	public boolean createNewUser(Login login) {
		boolean result = false;
		Bundle bundle = new Bundle();

		// login.setUser(user);
		login.setPassword(EncryptionUtil
				.getSecurePassword(Constants.RESET_PASSWORD));
		login.setActive(true);

		bundle.persist(login);
		result = true;

		bundle.closeConnection();

		return result;
	}

	/*
	 * public boolean updateUser(String username, User updateUser) { boolean
	 * result = false; Login login = finder.findByLoginUserName(username); User
	 * user = login.getUser();
	 * 
	 * if(!user.getName().equals(updateUser.getName())){
	 * addNewChangeHistory(username, Constants.USER_NAME, user.getName(),
	 * updateUser.getName()); user.setName(updateUser.getName()); }
	 * 
	 * if(!user.getRole().equals(updateUser.getRole())){
	 * addNewChangeHistory(username, Constants.USER_ROLE, user.getRole(),
	 * updateUser.getRole()); user.setRole(updateUser.getRole()); }
	 * 
	 * if(updateUser.getSupervisor() != null){ if((user.getSupervisor() ==
	 * null)){ addNewChangeHistory(username, Constants.USER_SUPERVISOR,
	 * Constants.NONE, updateUser.getSupervisor().toString());
	 * user.setSupervisor(updateUser.getSupervisor()); }else
	 * if(!user.getSupervisor().equals(updateUser.getSupervisor())){
	 * addNewChangeHistory(username, Constants.USER_SUPERVISOR,
	 * user.getSupervisor().toString(), updateUser.getSupervisor().toString());
	 * user.setSupervisor(updateUser.getSupervisor()); } } else {
	 * if(user.getSupervisor() != null){ addNewChangeHistory(username,
	 * Constants.USER_SUPERVISOR, user.getSupervisor().toString(),
	 * Constants.NONE); user.setSupervisor(null); } }
	 * 
	 * user.setAllowProfileCustomization(updateUser.isAllowProfileCustomization()
	 * );
	 * 
	 * bundle = new Bundle(); bundle.update(user); result = true;
	 * 
	 * bundle.closeConnection(); return result; }
	 */

	private boolean updateLogin(Login login) {
		boolean result = false;
		Bundle bundle = new Bundle();

		bundle.update(login);
		result = true;

		bundle.closeConnection();
		return result;
	}

}
