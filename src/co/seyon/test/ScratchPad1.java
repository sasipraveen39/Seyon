package co.seyon.test;

import co.seyon.cache.LRUList;
import co.seyon.util.EnvironmentUtil;

public class ScratchPad1 {

	public static void main(String[] args) {
		System.out.println(EnvironmentUtil.getImagePath("AC00", "PR001", "sample.jpeg", true));
		System.out.println(EnvironmentUtil.getImagePath("AC00", "PR001", "sample.jpeg", true));
		System.out.println(EnvironmentUtil.getImagePath("AC00", "PR001", "sample.jpeg", true));
		System.out.println(EnvironmentUtil.getImagePath("AC00", "PR001", "sample.jpeg", true));
	}
}
