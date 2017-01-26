package co.seyon.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Bundle {

	private EntityManagerFactory emfactory = null;
	private EntityManager entitymanager = null;

	public Bundle() {
		emfactory = Persistence.createEntityManagerFactory("Seyon");
		entitymanager = emfactory.createEntityManager();
	}
	
	public boolean persist(Object object) {
		boolean result = false;
		entitymanager.getTransaction().begin();
		entitymanager.persist(object);
		entitymanager.getTransaction().commit();
		result = true;
		return result;
	}
	
	
	public boolean persistAll(Object object[]) {
		boolean result = false;
		entitymanager.getTransaction().begin();
		for(Object o : object){
			entitymanager.persist(o);	
		}
		entitymanager.getTransaction().commit();
		result = true;
		return result;
	}
	
	public boolean update(Object object) {
		boolean result = false;
		entitymanager.getTransaction().begin();
		entitymanager.merge(object);
		entitymanager.getTransaction().commit();
		result = true;
		return result;
	}
	
	public void refresh(Object data){
		entitymanager.refresh(data);
	}
	
	public void closeConnection() {
		entitymanager.close();
		emfactory.close();
	}
}
