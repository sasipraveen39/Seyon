package co.seyon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import co.seyon.enums.BillType;
import co.seyon.enums.DocumentType;
import co.seyon.enums.ProjectType;
import co.seyon.enums.UserType;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Drawing;
import co.seyon.model.Login;
import co.seyon.model.Payment;
import co.seyon.model.Project;
import co.seyon.model.User;

public class Finder {

	private EntityManagerFactory emfactory = null;
	private EntityManager entitymanager = null;

	public Finder() {
		emfactory = Persistence.createEntityManagerFactory("Seyon");
	}

	private void createEntityManager(){
		entitymanager = emfactory.createEntityManager();
	}
	
	public Login findByLoginUserName(String userName) {
		createEntityManager();
		TypedQuery<Login> query = entitymanager.createNamedQuery(
				"Login.findByUserName", Login.class).setParameter("username",
				userName);
		Login result = null;
		try {
			result = query.getSingleResult();
			if(result != null){
				this.refresh(result);
			}
		} catch (NoResultException e) {
			result = null;
		}finally{
			closeConnection();			
		}
		
		
		return result;
	}

	public User findUserbyID(int userID) {
		createEntityManager();
		TypedQuery<User> query = entitymanager.createNamedQuery(
				"User.findbyID", User.class).setParameter("iduser", userID);
		User result = null;
		try {
			result = query.getSingleResult();
			if(result != null){
				this.refresh(result);
			}
		} catch (NoResultException e) {
			result = null;
		}finally{
			closeConnection();			
		}
		
		return result;
	}

	public List<User> findUsers(String accountNumber, String accountName, String mobileNumber, String email){
		createEntityManager();
		String queryString = "Select u from User u, Login l";
		List<String> constraints = new ArrayList<>();
		
		constraints.add("l = u.login");
		constraints.add("l.userType='"+UserType.CLIENT+"'");
		
		if(StringUtils.isNotBlank(accountNumber)){
			constraints.add( "u.accountNumber='"+accountNumber+"'");
		}
		
		if(StringUtils.isNotBlank(accountName)){
			constraints.add( "u.name like '"+accountName+"%'");
		}
		
		if(StringUtils.isNotBlank(mobileNumber)){
			constraints.add( "u.mobileNumber='"+mobileNumber+"'");
		}
		
		if(StringUtils.isNotBlank(email)){
			constraints.add( "u.email='"+email+"'");
		}
		
		if(constraints.size() > 0){
			queryString += " where "+ StringUtils.join(constraints, " and ");	
		}
		TypedQuery<User> query = entitymanager.createQuery(queryString, User.class);
		List<User> users = query.getResultList();
		if(users.size() > 0){
			for(User u : users){
				this.refresh(u);
			}
		}
		closeConnection();
		return users;
	}
	
	
	public List<Project> findProjects(String projectNumber, String title, String type, String pincode){
		createEntityManager();
		String queryString = "Select p from Project p, Address a";
		List<String> constraints = new ArrayList<>();
		constraints.add("p.address = a");
		
		if(StringUtils.isNotBlank(projectNumber)){
			constraints.add( "p.projectNumber='"+projectNumber+"'");
		}
		
		if(StringUtils.isNotBlank(title)){
			constraints.add( "p.title like '"+title+"%'");
		}
		
		if(StringUtils.isNotBlank(type) && !("none".equalsIgnoreCase(type))){
			constraints.add( "p.projectType='"+ProjectType.valueOf(type).getValue()+"'");
		}
		
		if(StringUtils.isNotBlank(pincode)){
			constraints.add( "a.pincode='"+pincode+"'");
		}
		
		if(constraints.size() > 0){
			queryString += " where "+ StringUtils.join(constraints, " and ");	
		}
		TypedQuery<Project> query = entitymanager.createQuery(queryString, Project.class);
		List<Project> projects = query.getResultList();
		if(projects.size() > 0){
			for(Project p : projects){
				this.refresh(p);
			}
		}
		closeConnection();
		return projects;
	}
	
	public List<Document> findDocuments(String documentNumber, DocumentType type){
		createEntityManager();
		String queryString = "Select d from Document d";
		List<String> constraints = new ArrayList<>();
		
		if(StringUtils.isNotBlank(documentNumber)){
			constraints.add( "d.documentNumber='"+documentNumber+"'");
		}
		
		if(type != null){
			constraints.add( "d.documentType like '"+type.toString()+"'");
		}
		
		if(constraints.size() > 0){
			queryString += " where "+ StringUtils.join(constraints, " and ");	
		}
		TypedQuery<Document> query = entitymanager.createQuery(queryString, Document.class);
		List<Document> documents = query.getResultList();
		if(documents.size() > 0){
			for(Document d : documents){
				this.refresh(d);
			}
		}
		closeConnection();
		return documents;
	}
	
	public List<Bill> findBills(String billNumber, BillType type){
		createEntityManager();
		String queryString = "Select b from Bill b";
		List<String> constraints = new ArrayList<>();
		
		if(StringUtils.isNotBlank(billNumber)){
			constraints.add( "b.billNumber='"+billNumber+"'");
		}
		
		if(type != null){
			constraints.add( "b.billType like '"+type.toString()+"'");
		}
		
		if(constraints.size() > 0){
			queryString += " where "+ StringUtils.join(constraints, " and ");	
		}
		TypedQuery<Bill> query = entitymanager.createQuery(queryString, Bill.class);
		List<Bill> bills = query.getResultList();
		if(bills.size() > 0){
			for(Bill d : bills){
				this.refresh(d);
			}
		}
		closeConnection();
		return bills;
	}
	
	public List<Drawing> findDrawings(String drawingNumber){
		createEntityManager();
		String queryString = "Select d from Drawing d";
		List<String> constraints = new ArrayList<>();
		
		if(StringUtils.isNotBlank(drawingNumber)){
			constraints.add( "d.drawingNumber='"+drawingNumber+"'");
		}
		
		if(constraints.size() > 0){
			queryString += " where "+ StringUtils.join(constraints, " and ");	
		}
		TypedQuery<Drawing> query = entitymanager.createQuery(queryString, Drawing.class);
		List<Drawing> drawings = query.getResultList();
		if(drawings.size() > 0){
			for(Drawing d : drawings){
				this.refresh(d);
			}
		}
		closeConnection();
		return drawings;
	}
	
	public List<Document> findDocumentsByID(List<Long> docIDs){
		createEntityManager();
		TypedQuery<Document> query = entitymanager
				.createQuery("SELECT d from Document d WHERE d.iddocument IN :ids", Document.class);
		query.setParameter("ids", docIDs);
		List<Document> documents = query.getResultList();
		closeConnection();
		return documents;
	}
	
	public List<Bill> findBillsByID(List<Long> billIDs){
		createEntityManager();
		TypedQuery<Bill> query = entitymanager
				.createQuery("SELECT b from Bill b WHERE b.idbill IN :ids", Bill.class);
		query.setParameter("ids", billIDs);
		List<Bill> bills = query.getResultList();
		closeConnection();
		return bills;
	}
	
	
	public List<Drawing> findDrawingsByID(List<Long> drawingIDs){
		createEntityManager();
		TypedQuery<Drawing> query = entitymanager
				.createQuery("SELECT d from Drawing d WHERE d.iddrawing IN :ids", Drawing.class);
		query.setParameter("ids", drawingIDs);
		List<Drawing> drawings = query.getResultList();
		closeConnection();
		return drawings;
	}
	
	public List<Project> findProjectsByID(List<Long> projectIDs){
		createEntityManager();
		TypedQuery<Project> query = entitymanager
				.createQuery("SELECT d from Project d WHERE d.idproject IN :ids", Project.class);
		query.setParameter("ids", projectIDs);
		List<Project> projects = query.getResultList();
		closeConnection();
		return projects;
	}
	
	public List<Payment> findPaymentsByID(List<Long> paymentIDs){
		createEntityManager();
		TypedQuery<Payment> query = entitymanager
				.createQuery("SELECT d from Payment d WHERE d.idpayment IN :ids", Payment.class);
		query.setParameter("ids", paymentIDs);
		List<Payment> payments = query.getResultList();
		closeConnection();
		return payments;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int findLastSequence(Class className) {
		createEntityManager();
		String sequence = null;
		int seqNum = -1;
		TypedQuery<Object> query = entitymanager.createNamedQuery(
				className.getSimpleName() + ".findAllByReverseNumber", className);
		entitymanager.setProperty("disableRetiredFeature", 1);
		query.setMaxResults(1);
		Object result = null;
		try{
			result = query.getSingleResult();
		}catch (Exception e) {
			result = null;
		}

		if(result != null){
			this.refresh(result);
		}
		
		if (result instanceof User) {
			sequence = ((User) result).getAccountNumber();
		} else if (result instanceof Project) {
			sequence = ((Project) result).getProjectNumber();
		} else if (result instanceof Bill) {
			sequence = ((Bill) result).getBillNumber();
		} else if (result instanceof Document) {
			sequence = ((Document) result).getDocumentNumber();
		} else if (result instanceof Payment){
			sequence = ((Payment) result).getPaymentNumber();
		}
		
		if(sequence != null){
			seqNum = Integer.parseInt(sequence.substring(2));
		}

		closeConnection();
		return seqNum;
	}
	
	private void refresh(Object data){
		entitymanager.refresh(data);
	}
	
	private void closeConnection() {
		entitymanager.close();
	}
}
