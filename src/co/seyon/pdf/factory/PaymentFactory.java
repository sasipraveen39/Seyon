package co.seyon.pdf.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import co.seyon.enums.AddressType;
import co.seyon.enums.BillStatus;
import co.seyon.enums.BillType;
import co.seyon.enums.DocumentType;
import co.seyon.enums.DrawingStatus;
import co.seyon.enums.DrawingType;
import co.seyon.enums.HistoryType;
import co.seyon.enums.PaymentStatus;
import co.seyon.enums.ProjectType;
import co.seyon.enums.UserType;
import co.seyon.model.Address;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Drawing;
import co.seyon.model.History;
import co.seyon.model.Login;
import co.seyon.model.Payment;
import co.seyon.model.Project;
import co.seyon.model.User;


public class PaymentFactory {

	public static Collection<Payment> generateCollection() {
		List<Payment> payments = new ArrayList<Payment>();
		
		Login login = new Login();
		login.setUsername("sasipraveen56");
		login.setPassword("sample");
		login.setActive(true);
		login.setUserType(UserType.CLIENT);
		
		User user = new User();
		user.setName("Praveen");
		user.setEmail("sasipraveen39@gmail.com");
		user.setMobileNumber("9790829078");
		user.setLandlineNumber("04442817868");
		user.setAccountNumber("AC00000244");
		
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
		project.setProjectType(ProjectType.RESEDENTIAL);
		project.setCode("1234");
		project.setClientName("Sasi Praveen");
		project.setAddress(address);
		project.setTotalAreaOfProject(2500);
		project.setEstimatedEndDate(new Date());
		project.setStartDate(new Date());
		project.setRequestedDate(new Date());
		project.setEstimatedTotalAmount(new BigDecimal(1000.12));
		project.setProjectNumber("PR00002342454");
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
		drawing.setStatus(DrawingStatus.DRAFT);
		drawing.setProject(project);
		
		Bill bill = new Bill();
		bill.setBillDate(new Date());
		bill.setBillType(BillType.ADVANCE);
		bill.setBillNumber("12345");
		bill.setTotalBillAmount(new BigDecimal(2000.33));
		bill.setDocument(document);
		bill.setBillStatus(BillStatus.DRAFT);
		bill.setProject(project);
		
		Payment payment = new Payment();
		payment.setStatus(PaymentStatus.PAID);
		payment.setDueDate(new Date());
		payment.setDocument(document);
		payment.setBill(bill);
		payment.setAmountPayable(new BigDecimal(100.22));
		
		payments.add(payment);
		
		return payments;
	}
}
