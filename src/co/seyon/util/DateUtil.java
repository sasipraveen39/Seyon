package co.seyon.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final Date getCurrentTimestamp() {
		java.util.Date utilDate = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(utilDate);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());
	}

	public static final Date getCurrentDayStart() {
		java.util.Date utilDate = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static final String getDayString(Date date) {
		java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyyMMdd");
		return fmt.format(date);
	}
	
	public static final Date getDay(String dateString) throws ParseException {
		java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyyMMdd");
		return fmt.parse(dateString);
	}
	
	public static final String getStandardDate(Date date) {
		java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(Constants.DATE_FORMAT);
		return fmt.format(date);
	}
	
	public static final String getTimeStampLabel(){
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		String label = year
				+ "_" +(month+1)+ "_" + day + "_"
				+ hour+ "_" + minute;
		return label;
	}
}
