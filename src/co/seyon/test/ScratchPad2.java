package co.seyon.test;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.model.User;

public class ScratchPad2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Finder finder = new Finder();
		User user = finder.findUsers("AC00000014", null, null, null).get(0);
		System.out.println(user.getLogin());
		
		user.setEmail("sas");
		Bundle bundle = new Bundle();
		bundle.update(user);
		bundle.closeConnection();
		
		User user2 =  finder.findUsers("AC00000014", null, null, null).get(0);
		System.out.println(user2.getLogin());
	}

}
