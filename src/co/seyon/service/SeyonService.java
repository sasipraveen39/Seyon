package co.seyon.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.enums.AddressType;
import co.seyon.enums.DocumentType;
import co.seyon.enums.UserType;
import co.seyon.exception.InitialPasswordException;
import co.seyon.exception.UserDeActiveException;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Drawing;
import co.seyon.model.Login;
import co.seyon.model.Payment;
import co.seyon.model.Project;
import co.seyon.model.User;
import co.seyon.pdf.generator.ReportGenerator;
import co.seyon.sequence.SequenceGenerator;
import co.seyon.util.Constants;
import co.seyon.util.DateUtil;
import co.seyon.util.EncryptionUtil;
import co.seyon.util.EnvironmentUtil;

public class SeyonService {

	private Finder finder;

	public SeyonService() {
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

	public boolean createNewProject(Project project) {
		boolean result = false;
		Bundle bundle = new Bundle();

		User user = finder.findUsers(project.getUser().getAccountNumber(),
				null, null, null).get(0);
		project.setUser(user);
		project.setProjectNumber(SequenceGenerator
				.generateSequence(Project.class));
		project.setCode(project.getProjectNumber());
		project.getAddress().setAddressType(AddressType.PROJECT_SITE);

		bundle.persist(project);
		result = true;

		bundle.closeConnection();

		return result;
	}

	public boolean updateProject(Project project) {
		boolean result = false;
		Bundle bundle = new Bundle();

		bundle.update(project);
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

	public boolean updateDrawing(String projectNumber, Drawing drawing,
			MultipartFile multipartFile) throws IOException {
		boolean result = false;
		if (updateDocument(projectNumber, drawing.getDocument(), multipartFile)) {
			Bundle bundle = new Bundle();
			bundle.update(drawing);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean updateBill(String projectNumber, Bill bill,
			MultipartFile multipartFile) throws IOException {
		boolean result = false;
		if (updateDocument(projectNumber, bill.getDocument(), multipartFile)) {
			Bundle bundle = new Bundle();
			bundle.update(bill);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean updateDocument(String projectNumber, Document document,
			MultipartFile multipartFile) throws IOException {
		boolean result = false;

		if ((multipartFile != null) && (!multipartFile.isEmpty())) {
			Project project = finder.findProjects(projectNumber, null, null,
					null).get(0);
			String docFileName = EnvironmentUtil.getDocumentPath(project
					.getUser().getAccountNumber(), project.getProjectNumber(),
					multipartFile.getOriginalFilename(), true);
			File serverDocFile = new File(docFileName);
			if (!serverDocFile.getParentFile().exists()) {
				serverDocFile.getParentFile().mkdirs();
			}
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
					serverDocFile);
			document.setFileLocation(EnvironmentUtil.getExposedDocumentPath(
					project.getUser().getAccountNumber(),
					project.getProjectNumber(), serverDocFile.getName()));
		}

		Bundle bundle = new Bundle();

		bundle.update(document);
		result = true;

		bundle.closeConnection();
		return result;
	}

	public boolean createNewDocument(String projectNumber, Document document) {
		boolean result = false;
		Project project = finder.findProjects(projectNumber, null, null, null)
				.get(0);
		document.setProject(project);
		document.setDocumentNumber(SequenceGenerator
				.generateSequence(Document.class));
		Bundle bundle = new Bundle();
		bundle.persist(document);
		bundle.closeConnection();
		result = true;
		return result;
	}

	public boolean createDrawing(Drawing drawing) {
		boolean result = false;
		if (drawing != null) {
			drawing.getDocument().setDocumentNumber(
					SequenceGenerator.generateSequence(Document.class));
			Bundle bundle = new Bundle();
			bundle.persist(drawing);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean createDocument(Document document) {
		boolean result = false;
		if (document != null) {
			document.setDocumentNumber(SequenceGenerator
					.generateSequence(Document.class));
			Bundle bundle = new Bundle();
			bundle.persist(document);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean createBill(Bill bill) {
		boolean result = false;
		if (bill != null) {
			bill.setBillNumber(SequenceGenerator.generateSequence(Bill.class));
			bill.getDocument().setDocumentNumber(
					SequenceGenerator.generateSequence(Document.class));
			Bundle bundle = new Bundle();
			bundle.persist(bill);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean createPayment(Payment payment) {
		boolean result = false;
		if (payment != null) {
			payment.setDocument(null);
			payment.setPaymentNumber(SequenceGenerator
					.generateSequence(Payment.class));
			Bundle bundle = new Bundle();
			bundle.persist(payment);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean deleteDocuments(List<Long> docIDs) {
		boolean result = false;
		List<Document> documents = finder.findDocumentsByID(docIDs);
		if (documents != null) {
			Bundle bundle = new Bundle();
			bundle.removeAll(documents);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean deleteBills(List<Long> billIDs) {
		boolean result = false;
		List<Bill> bills = finder.findBillsByID(billIDs);
		if (bills != null) {
			Bundle bundle = new Bundle();
			bundle.removeAll(bills);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean deleteDrawings(List<Long> drawingIDs) {
		boolean result = false;
		List<Drawing> drawings = finder.findDrawingsByID(drawingIDs);
		if (drawings != null) {
			Bundle bundle = new Bundle();
			bundle.removeAll(drawings);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean deleteProjects(List<Long> projectIDs) {
		boolean result = false;
		List<Project> projects = finder.findProjectsByID(projectIDs);
		if (projects != null) {
			Bundle bundle = new Bundle();
			bundle.removeAll(projects);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean deletePayments(List<Long> paymentIDs) {
		boolean result = false;
		List<Payment> payments = finder.findPaymentsByID(paymentIDs);
		if (payments != null) {
			Bundle bundle = new Bundle();
			bundle.removeAll(payments);
			bundle.closeConnection();
		}
		result = true;
		return result;
	}

	public boolean generateReceipt(Payment payment) {
		boolean result = false;
		Project project = payment.getBill().getProject();
		String docFileName = EnvironmentUtil.getDocumentPath(project.getUser()
				.getAccountNumber(), project.getProjectNumber(), "Receipt.pdf",
				true);
		File serverDocFile = new File(docFileName);
		if (!serverDocFile.getParentFile().exists()) {
			serverDocFile.getParentFile().mkdirs();
		}

		ReportGenerator generator = new ReportGenerator();
		payment.setReceiptDate(new Date());
		payment.setReceiptNumber(SequenceGenerator.generateReceiptSequence());
		if (generator.generateReceipt(payment, docFileName)) {
			Document document = new Document();
			document.setProject(project);
			document.setName("Receipt for Payment "
					+ payment.getPaymentNumber());
			document.setDescription("Receipt for Payment "
					+ payment.getPaymentNumber());
			document.setDocumentType(DocumentType.RECEIPT);
			document.setFileLocation(EnvironmentUtil.getExposedDocumentPath(
					project.getUser().getAccountNumber(),
					project.getProjectNumber(), serverDocFile.getName()));
			payment.setDocument(document);
			if (this.createDocument(document)) {
				result = true;
			}
		}
		return result;
	}
}
