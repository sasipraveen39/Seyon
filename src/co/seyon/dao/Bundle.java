package co.seyon.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import co.seyon.model.Address;
import co.seyon.model.Login;
import co.seyon.model.User;
import co.seyon.util.Constants;

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
		Finder fin = new Finder();
		
		Object target = null;
		if(object instanceof Login){
			target = object;
		} else if(object instanceof User){
			User src = (User)object;
			target = fin.findUsers(src.getAccountNumber(),null, null, null).get(0);
			User tgt = (User)target;
			copyProperties(src, tgt, Constants.USER_PROPERTIES_TO_AVOID);
			copyProperties(src.getAddress(),tgt.getAddress(), Constants.ADDRESS_PROPERTIES_TO_AVOID);
		}
		entitymanager.getTransaction().begin();
		entitymanager.merge(target);
		entitymanager.getTransaction().commit();
		
		result = true;
		return result;
	}
	
	public void refresh(Object data){
		entitymanager.refresh(data);
	}
	
	public boolean copyProperties(Object source, Object target, String[] propertiesToAvoid){
		List<String> propToAvoid = Arrays.asList(propertiesToAvoid);
		final BeanWrapper src = new BeanWrapperImpl(source);
		final BeanWrapper tgt = new BeanWrapperImpl(target);
		PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
		
		for(PropertyDescriptor pd : propertyDescriptors){
			if(pd.getWriteMethod() != null){
				if(!propToAvoid.contains(pd.getName())){
					Object srcValue = null;
					try {
						srcValue = pd.getReadMethod().invoke(source);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
						return false;
					}
					if(! ((srcValue instanceof Address) || (srcValue instanceof User))){
						tgt.setPropertyValue(pd.getName(), srcValue);
					}
				}	
			}
		}
		return true;
	}
	
	public void closeConnection() {
		entitymanager.close();
		emfactory.close();
	}
}
