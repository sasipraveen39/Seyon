package co.seyon.test;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.model.Project;


public class ScratchPad2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Finder finder = new Finder();
		Project project = finder.findProjects("PR00000013", null, null, null).get(0);
		Bundle bundle = new Bundle();
		bundle.remove(project);
		bundle.closeConnection();
		project = finder.findProjects("PR00000013", null, null, null).get(0);
		System.out.println(project);
		System.out.println("End");
	}

}
