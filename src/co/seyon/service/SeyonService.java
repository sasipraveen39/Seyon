package co.seyon.service;

import java.text.SimpleDateFormat;
import java.util.List;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.enums.AddressType;
import co.seyon.enums.UserType;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Drawing;
import co.seyon.model.Login;
import co.seyon.model.Project;
import co.seyon.model.User;
import co.seyon.sequence.SequenceGenerator;
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

		login.setUserType(UserType.CLIENT);
		login.setPassword(EncryptionUtil
				.getSecurePassword(Constants.RESET_PASSWORD));
		login.setActive(true);

		login.getUser().setAccountNumber(
				SequenceGenerator.generateSequence(User.class));
		login.getUser().getAddress().setAddressType(AddressType.BILLING);
		bundle.persist(login);
		result = true;

		bundle.closeConnection();

		return result;
	}

	public boolean updateUser(User user) {
		boolean result = false;
		Bundle bundle = new Bundle();
		
		bundle.update(user);
		result = true;
		
		bundle.closeConnection();
		return result; 
	}

	private boolean updateLogin(Login login) {
		boolean result = false;
		Bundle bundle = new Bundle();

		bundle.update(login);
		result = true;

		bundle.closeConnection();
		return result;
	}

	public boolean createNewDocument(String projectNumber, Document document){
		boolean result = false;
		Project project = finder.findProjects(projectNumber, null, null, null).get(0);
		document.setProject(project);
		Bundle bundle = new Bundle();
		bundle.persist(document);
		bundle.closeConnection();
		result = true;
		return result;
	}
	
	public boolean createDrawing(Drawing drawing){
		boolean result = false;
		if(drawing != null){
			Bundle bundle = new Bundle();
			bundle.persist(drawing);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}
	
	public boolean createDocument(Document document){
		boolean result = false;
		if(document != null){
			Bundle bundle = new Bundle();
			bundle.persist(document);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}
	
	public boolean deleteDocuments(List<Long> docIDs){
		boolean result = false;
		List<Document> documents = finder.findDocumentsByID(docIDs);
		if(documents != null){
			Bundle bundle = new Bundle();
			bundle.removeAll(documents);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}
	
	public boolean deleteBills(List<Long> billIDs){
		boolean result = false;
		List<Bill> bills = finder.findBillsByID(billIDs);
		if(bills != null){
			Bundle bundle = new Bundle();
			bundle.removeAll(bills);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}
	
	public boolean deleteDrawings(List<Long> drawingIDs){
		boolean result = false;
		List<Drawing> drawings = finder.findDrawingsByID(drawingIDs);
		if(drawings != null){
			Bundle bundle = new Bundle();
			bundle.removeAll(drawings);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}
	
}
