package co.seyon.util;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

public class EnvironmentUtil {
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	
	private static long uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return now;
	    }
	}
	
	public static String getImagePath(String acctNum, String projNum, String imageName, boolean generateName){
		String basePath = FileProperty.getInstance().getProperty(FileProperty.SAVE_PATH);
		basePath = basePath.replace("\\", File.separator);
		if(generateName){
			imageName = "IMG"+uniqueCurrentTimeMS()+imageName.substring(imageName.lastIndexOf("."), imageName.length());
		}
		String path = basePath + File.separator + acctNum + File.separator + 
	    		   projNum + File.separator +"Images"+ File.separator + imageName;
		return path;
	}
	
	
	public static String getDocumentPath(String acctNum, String projNum, String docName, boolean generateName){
		String basePath = FileProperty.getInstance().getProperty(FileProperty.SAVE_PATH);
		basePath = basePath.replace("\\", File.separator);
		if(generateName){
			docName = "DOC"+uniqueCurrentTimeMS()+docName.substring(docName.lastIndexOf("."), docName.length());
		}
		String path = basePath + File.separator + acctNum + File.separator + 
	    		   projNum + File.separator +"Documents"+ File.separator + docName;
		return path;
	}
}
