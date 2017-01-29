package co.seyon.test;

import co.seyon.cache.LRUList;

public class ScratchPad1 {

	public static void main(String[] args) {
		LRUList<String> lruList = new LRUList<>(5);
		
		lruList.add("A");
		lruList.add("B");
		lruList.add("C");
		lruList.add("D");
		lruList.add("E");
		lruList.add("B");
		lruList.add("F");
		
		for(String s : lruList){
			System.out.println(s);
		}
	}
}
