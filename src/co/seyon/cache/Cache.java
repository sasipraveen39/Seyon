package co.seyon.cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	private static final int LRU_SIZE = 5;
	private static final ConcurrentHashMap<String, LRUList<String>> RECENT_ACCOUNT_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, LRUList<String>> RECENT_PROJECT_MAP = new ConcurrentHashMap<>();
	
	public static void addToLRUAccounts(String userid, String accountNumber){
		RECENT_ACCOUNT_MAP.putIfAbsent(userid, new LRUList<String>(LRU_SIZE));
		RECENT_ACCOUNT_MAP.get(userid).add(accountNumber);
	}
	
	public static List<String> getLRUAccounts(String userid){
		return RECENT_ACCOUNT_MAP.get(userid);
	}
	
	public static void addToLRUProjects(String userid, String projectNumber){
		RECENT_PROJECT_MAP.putIfAbsent(userid, new LRUList<String>(LRU_SIZE));
		RECENT_PROJECT_MAP.get(userid).add(projectNumber);
	}
	
	public static List<String> getLRUProjects(String userid){
		return RECENT_PROJECT_MAP.get(userid);
	}
}
