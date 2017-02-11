package co.seyon.test;

import co.seyon.dao.Bundle;
import co.seyon.dao.Finder;
import co.seyon.enums.DocumentType;
import co.seyon.model.Document;
import co.seyon.model.Project;
import co.seyon.model.User;

public class ScratchPad2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Finder finder = new Finder();
		User user = finder.findUsers("AC00000013", null, null, null).get(0);
		Project project = user.getProjects().get(0);
		Bundle bundle = new Bundle();
		Document document = new Document();
		document.setName("Image2");
		document.setFileLocation("images/AC00000013/PR00000013/IMG-20160720-WA0028.jpg");
		document.setProject(project);
		document.setDocumentType(DocumentType.IMAGE);
		bundle.persist(document);
		bundle.closeConnection();
		System.out.println("end");
	}

}
