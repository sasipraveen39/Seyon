package co.seyon.test;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.model.Project;
import co.seyon.model.User;


public class ScratchPad3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Finder finder = new Finder();
		User user = finder.findUsers("AC00000004", null, null, null).get(0);
		Bundle bundle = new Bundle();
		bundle.remove(user.getLogin());
		bundle.closeConnection();
		user = finder.findUsers("AC00000004", null, null, null).get(0);
		System.out.println(user);
		System.out.println("End");
	}

}
