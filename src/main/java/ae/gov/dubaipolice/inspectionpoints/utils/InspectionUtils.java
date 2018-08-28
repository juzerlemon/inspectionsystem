package ae.gov.dubaipolice.inspectionpoints.utils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.omnifaces.util.Faces;

public class InspectionUtils implements Serializable {
	
	public static final Integer NEW_STATUS=10;
	public static final Integer ACTIVE_STATUS=20;
	public static final Integer APPROVED_STAUS=30;
	public static final Integer REJECT_STAUS=40;
	

	private static final long serialVersionUID = 1L;

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public  static String theMonth(int month){
	    String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
	    return monthNames[month];
	}
	
	public static Date getDateWithStartTime(Date date) {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		return cal1.getTime();
	}
	
	public static String getMessageKey(String messageKey) {
        return Faces.getContext().getApplication().evaluateExpressionGet(Faces.getContext(), "#{msg['" + messageKey + "']}", String.class);
    }
}
