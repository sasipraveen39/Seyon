package co.seyon.test;

import java.math.BigDecimal;
import java.util.Date;

import co.seyon.dao.Bundle;
import co.seyon.enums.AddressType;
import co.seyon.enums.BillType;
import co.seyon.enums.DocumentType;
import co.seyon.enums.DrawingType;
import co.seyon.enums.HistoryType;
import co.seyon.enums.UserType;
import co.seyon.model.*;
import co.seyon.sequence.SequenceGenerator;
import co.seyon.util.EncryptionUtil;

public class ScratchPad {

	public static void main(String[] args) {
		System.out.println("Start");
		
		Bundle bundle = new Bundle();
		
		Login login = new Login();
		login.setUsername("sasipraveen11");
		login.setPassword(EncryptionUtil.getSecurePassword("sample"));
		login.setActive(true);
		login.setUserType(UserType.VENDOR);
		
		User user = new User();
		user.setName("Sasi Praveen");
		user.setEmail("sasipraveen39@gmail.com");
		user.setMobileNumber("9790829078");
		user.setLandlineNumber("04442817868");
		user.setAccountNumber(SequenceGenerator.generateSequence(User.class));
		
		Address address = new Address();
		address.setAddressLine1("No. 108, T.K.Mudali St, Choolai");
		address.setState("Tamil Nadu");
		address.setCity("Chennai");
		address.setCountry("India");
		address.setPincode("600112");
		address.setAddressType(AddressType.BILLING);
		
		user.setAddress(address);
		login.setUser(user);
		
		Project project = new Project();
		project.setTitle("Sample Project1");
		project.setProjectType("COMMERCIAL");
		project.setCode("1234");
		project.setClientName("Sasi Praveen");
		project.setAddress(address);
		project.setTotalAreaOfProject(2500);
		project.setEstimatedEndDate(new Date());
		project.setStartDate(new Date());
		project.setRequestedDate(new Date());
		project.setEstimatedTotalAmount(new BigDecimal(1000.12));
		project.setProjectNumber(SequenceGenerator.generateSequence(Project.class));
		project.setUser(user);
		
		History history = new History();
		history.setChangeReason("Sample");
		history.setFieldName("Sample");
		history.setCurrentValue("Sample");
		history.setProject(project);
		history.setHistoryType(HistoryType.ADD);
		history.setPreviousValue("<none>");
		
		Document document = new Document();
		document.setName("Sample");
		document.setFileLocation("Sample");
		document.setDocumentType(DocumentType.BILL);
		document.setProject(project);
		
		Drawing drawing = new Drawing();
		drawing.setDateOfIssue(new Date());
		drawing.setDrawingNumber("1234");
		drawing.setTypeOfDrawing(DrawingType.STRUCTURAL);
		drawing.setDocument(document);
		drawing.setStatus("FINAL");
		drawing.setProject(project);
		
		Bill bill = new Bill();
		bill.setBillDate(new Date());
		bill.setBillType(BillType.ADVANCE);
		bill.setBillNumber("12345");
		bill.setTotalBillAmount(new BigDecimal(2000.33));
		bill.setDocument(document);
		bill.setBillStatus("Pending");
		bill.setProject(project);
		
		Payment payment = new Payment();
		payment.setStatus("not Paid");
		payment.setDueDate(new Date());
		payment.setDocument(document);
		payment.setBill(bill);
		payment.setAmountPayable(new BigDecimal(100.22));
		
		bundle.persistAll(new Object[]{login,user, address, project, drawing, document, bill, payment, history});
		bundle.closeConnection();
		
		System.out.println("Complete");
	}
}
