package co.seyon.util;

public class Constants {
	public static final String RESET_PASSWORD = "RESETMYSELF111$$111";

	public static final String DATE_FORMAT = "MM/dd/YYYY";
	public static final String DATE_TIME_FORMAT = "MM/dd/YYYY K:mm:ss a";
	public static final String[] USER_PROPERTIES_TO_AVOID = { "iduser",
			"createTime" };
	public static final String[] DOCUMENT_PROPERTIES_TO_AVOID = { "iddocument",
			"createTime", "documentType", "project" };
	public static final String[] DRAWING_PROPERTIES_TO_AVOID = { "iddrawing",
			"createTime", "project" };
	public static final String[] BILL_PROPERTIES_TO_AVOID = { "idbill",
			"createTime", "project" };
	public static final String[] PROJECT_PROPERTIES_TO_AVOID = { "idproject",
			"createTime", "bills", "documents", "drawings", "histories" };
	public static final String[] ADDRESS_PROPERTIES_TO_AVOID = { "idaddress",
			"addressType", "createTime" };
}
