package co.seyon.util;

import java.io.File;

public class Constants {
	public static final String RESET_PASSWORD = "RESETMYSELF111$$111";
	
	public static final String DATE_FORMAT= "MM/dd/YYYY";
	public static final String DATE_TIME_FORMAT= "MM/dd/YYYY K:mm:ss a";
	public static final String[] USER_PROPERTIES_TO_AVOID = {"iduser","createTime"};
	public static final String[] ADDRESS_PROPERTIES_TO_AVOID = {"idaddress","addressType"};
	
	public static final String SAVE_PATH= "D:"+File.separator+"SeyonFiles";
}
