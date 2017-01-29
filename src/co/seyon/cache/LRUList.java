package co.seyon.cache;

import java.util.LinkedList;

public class LRUList<E> extends LinkedList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4653349389257614078L;
	private int maxSize = 0;

	public LRUList(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public boolean add(E e) {
		int existingIndex = super.indexOf(e);
		if (existingIndex == -1) {
			super.addFirst(e);
		} else {
			super.addFirst(super.remove(existingIndex));
		}

		if (super.size() > maxSize) {
			super.removeRange(maxSize, super.size());
		}
		return true;
	}

}
