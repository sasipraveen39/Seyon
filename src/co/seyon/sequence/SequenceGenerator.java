package co.seyon.sequence;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import co.seyon.dao.Finder;
import co.seyon.model.Bill;
import co.seyon.model.Document;
import co.seyon.model.Payment;
import co.seyon.model.Project;
import co.seyon.model.User;

public class SequenceGenerator {
	private static final String RECEIPT = "Receipt";

	private static final ConcurrentHashMap<Class, AtomicInteger> map = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, AtomicInteger> receiptMap = new ConcurrentHashMap<>();

	private SequenceGenerator() {
	}

	public static String generateSequence(Class className) {
		String prefix = null;
		AtomicInteger atomicInteger = map.get(className);
		if (atomicInteger == null) {
			int initialValue = new Finder().findLastSequence(className) + 1;
			atomicInteger = new AtomicInteger(initialValue == 0 ? 1
					: initialValue);
			map.put(className, atomicInteger);
		}
		if (className == User.class) {
			prefix = "AC";
		} else if (className == Project.class) {
			prefix = "PR";
		} else if (className == Bill.class) {
			prefix = "BL";
		} else if (className == Document.class) {
			prefix = "DT";
		} else if (className == Payment.class) {
			prefix = "PY";
		}
		return prefix + String.format("%08d", atomicInteger.getAndIncrement());
	}

	public static String generateReceiptSequence() {
		String prefix = null;
		AtomicInteger atomicInteger = receiptMap.get(RECEIPT);
		if (atomicInteger == null) {
			int initialValue = new Finder().findLastReceiptSequence() + 1;
			atomicInteger = new AtomicInteger(initialValue == 0 ? 1
					: initialValue);
			receiptMap.put(RECEIPT, atomicInteger);
		}
		prefix = "RT";
		return prefix + String.format("%08d", atomicInteger.getAndIncrement());
	}

}
