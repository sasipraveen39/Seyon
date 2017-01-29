package co.seyon.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import co.seyon.model.Login;
import co.seyon.model.Project;
import co.seyon.model.User;

public class Finder {

	private EntityManagerFactory emfactory = null;
	private EntityManager entitymanager = null;

	public Finder() {
		emfactory = Persistence.createEntityManagerFactory("Seyon");
		entitymanager = emfactory.createEntityManager();
	}

	public Login findByLoginUserName(String userName) {
		TypedQuery<Login> query = entitymanager.createNamedQuery(
				"Login.findByUserName", Login.class).setParameter("username",
				userName);
		Login result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

	public User findUserbyID(int userID) {
		TypedQuery<User> query = entitymanager.createNamedQuery(
				"User.findbyID", User.class).setParameter("iduser", userID);
		User result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

	public int findLastSequence(Class className) {
		String sequence = null;
		int seqNum = -1;
		TypedQuery<Object> query = entitymanager.createNamedQuery(
				className.getSimpleName() + ".findAllByReverseNumber", className);
		query.setMaxResults(1);
		Object result = null;
		try{
			result = query.getSingleResult();
		}catch (Exception e) {
			result = null;
		}

		if (result instanceof User) {
			sequence = ((User) result).getAccountNumber();
		} else if (result instanceof Project) {
			sequence = ((Project) result).getProjectNumber();
		}
		
		if(sequence != null){
			seqNum = Integer.parseInt(sequence.substring(2));
		}

		return seqNum;
	}
}
