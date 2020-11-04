package com.etree.commons.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.exception.EtreeCommonsException;

public class CommonSupportDateUtils {

	private static final String DATE_TIME_PATTERN = "^([0-9]{2,4})(/|-)(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(T|\\s)*(?:(([0-2][0-9]):([0-5][0-9])(?::([0-5][0-9]))?)(?:(.\\d{3})?)(?:(Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])?))?$";

	private static Map<Integer, String> mapofMMM = null;
	private static Map<Integer, String> mapofMMMM = null;
	
	static {
		mapofMMM = new HashMap<>();
		mapofMMMM = new HashMap<>();
		mapofMMM.put(1, "Jan");
		mapofMMM.put(2, "Feb");
		mapofMMM.put(3, "Mar");
		mapofMMM.put(4, "Apr");
		mapofMMM.put(5, "May");
		mapofMMM.put(6, "Jun");
		mapofMMM.put(7, "Jul");
		mapofMMM.put(8, "Aug");
		mapofMMM.put(9, "Sep");
		mapofMMM.put(10, "Oct");
		mapofMMM.put(11, "Nov");
		mapofMMM.put(12, "Dec");

		mapofMMMM.put(1, "January");
		mapofMMMM.put(2, "February");
		mapofMMMM.put(3, "March");
		mapofMMMM.put(4, "April");
		mapofMMMM.put(5, "May");
		mapofMMMM.put(6, "June");
		mapofMMMM.put(7, "July");
		mapofMMMM.put(8, "August");
		mapofMMMM.put(9, "September");
		mapofMMMM.put(10, "October");
		mapofMMMM.put(11, "November");
		mapofMMMM.put(12, "December");
	}

	public static String getMonthShortName(int mm) {
		return mapofMMM.get(mm);
	}
	
	public static String getMonthLongName(int mm) {
		return mapofMMMM.get(mm);
	}
	
	public static String convert(XMLGregorianCalendar calendar, String timezone,
			CommonsSupportConstants.DATE_FORMAT format) {
		ZoneId zoneId = getZoneId(timezone);
		DateTimeFormatter formatter = getDateFormatter(format);
		ZonedDateTime zonedDateTime = calendar.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(zoneId);
		String dateWithTZ = zonedDateTime.format(formatter);
		return dateWithTZ;
	}

	public static String convert(String dateStr, String timezone, CommonsSupportConstants.DATE_FORMAT format) {
		ZoneId zoneId = getZoneId(timezone);
		DateTimeFormatter formatter = getDateFormatter(format);
		LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
		String dateWithTZ = zonedDateTime.format(formatter);
		return dateWithTZ;
	}

	public static String convert(Long timeInMillis, String timezone, CommonsSupportConstants.DATE_FORMAT format) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTimeInMillis(timeInMillis);
		String dateWithTZ = null;
		try {
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
			dateWithTZ = convert(calendar, timezone, format);
		} catch (Exception e) {
			throw new EtreeCommonsException("", e);
		}
		return dateWithTZ;
	}

	public static ZoneId getZoneId(String timezone) {
		ZoneId zoneId = null;
		if (timezone == null) {
			zoneId = ZoneId.systemDefault();
		} else if (ZoneId.SHORT_IDS.get(timezone) != null) {
			zoneId = ZoneId.of(ZoneId.SHORT_IDS.get(timezone));
		} else {
			zoneId = ZoneId.of(timezone);
		}
		return zoneId;
	}

	public static DateTimeFormatter getDateFormatter(CommonsSupportConstants.DATE_FORMAT format) {
		DateTimeFormatter formatter = null;
		if (format == null) {
			formatter = DateTimeFormatter
					.ofPattern(CommonsSupportConstants.DATE_FORMAT.YYYY_MM_DD_HH_MM_SS_SSS.value());
		} else {
			formatter = DateTimeFormatter.ofPattern(format.value());
		}
		return formatter;
	}

	public static String getFormat(String date) {
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile(DATE_TIME_PATTERN);
		Matcher matcher = pattern.matcher(date);
		matcher.reset();
		if (matcher.find()) {
			sb.append(matcher.group(1) != null ? "yyyy" : "");
			sb.append(matcher.group(2) != null ? matcher.group(2) : "/");
			sb.append(matcher.group(3) != null ? "MM" : "");
			sb.append(matcher.group(4) != null ? matcher.group(4) : "/");
			sb.append(matcher.group(5) != null ? "dd" : "");
			if (matcher.group(6) != null) {
				if (matcher.group(6).equalsIgnoreCase("T")) {
					sb.append("'T'");
				} else {
					sb.append(matcher.group(6));
				}
			} else {
				sb.append("");
			}
			sb.append(matcher.group(8) != null ? "hh" : "");
			sb.append(matcher.group(9) != null ? ":mm" : "");
			sb.append(matcher.group(10) != null ? ":ss" : "");
			sb.append(matcher.group(11) != null ? ".SSS" : "");
			if (matcher.group(12) != null) {
				if (matcher.group(12).equalsIgnoreCase("Z")) {
					sb.append("'Z'");
				} else {
					sb.append("Z");
				}
			} else {
				sb.append("");
			}
		}
		return sb.toString();
	}

	public static String getPattern(String dateString) {
		if (dateString == null) {
			return null;
		}
		StringBuffer patternBuilder = new StringBuffer();
		Pattern pattern = Pattern.compile(DATE_TIME_PATTERN);
		Matcher matcher = pattern.matcher(dateString);
		matcher.reset();
		if (matcher.find()) {
			String grp = matcher.group(1);
			patternBuilder.append(grp != null ? "yyyy" : "");
			grp = matcher.group(2);
			patternBuilder.append(grp != null ? matcher.group(2) : "/");
			grp = matcher.group(3);
			patternBuilder.append(grp != null ? "MM" : "");
			grp = matcher.group(4);
			patternBuilder.append(grp != null ? matcher.group(4) : "/");
			grp = matcher.group(5);
			patternBuilder.append(grp != null ? "dd" : "");
			grp = matcher.group(6);
			if (grp != null) {
				if (grp.equalsIgnoreCase("T")) {
					patternBuilder.append("'T'");
				} else {
					patternBuilder.append(grp);
				}
			}
			grp = matcher.group(8);
			patternBuilder.append(grp != null ? "hh" : "");
			grp = matcher.group(9);
			patternBuilder.append(grp != null ? ":mm" : "");
			grp = matcher.group(10);
			patternBuilder.append(grp != null ? ":ss" : "");
			grp = matcher.group(11);
			patternBuilder.append(grp != null ? ".SSS" : "");
			grp = matcher.group(12);
			patternBuilder.append(grp != null ? grp : "");
		}
		return patternBuilder.toString();
	}

	public static String getPattern(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		String date = calendar.toString();
		return getFormat(date);
	}

	public static SimpleDateFormat getSimpleDateFormat(XMLGregorianCalendar calendar) {
		if (calendar == null) {
			return null;
		}
		String dateString = calendar.toString();
		String pattern = getPattern(dateString);
		return new SimpleDateFormat(pattern);
	}

	public static String createCurrentTimeString(CommonsSupportConstants.DATE_FORMAT format) {
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		DateFormat zuluTime = new SimpleDateFormat(format.value());
		zuluTime.setTimeZone(TimeZone.getTimeZone("UTC"));
		return zuluTime.format(now);
	}

	public static String retrieveDate(XMLGregorianCalendar xmlGregorianCalendar) {
		return retrieveDate(xmlGregorianCalendar.toString());
	}

	public static String retrieveDate(String timestamp) {
		if (timestamp == null || !timestamp.contains("T")) {
			return timestamp;
		}
		timestamp = timestamp.substring(0, timestamp.indexOf("T"));
		return timestamp;
	}

	public static String retrieveTime(XMLGregorianCalendar xmlGregorianCalendar) {
		return retrieveTime(xmlGregorianCalendar.toString());
	}

	public static String retrieveTime(String timestamp) {
		if (timestamp == null || !timestamp.contains("T")) {
			return timestamp;
		}
		timestamp = timestamp.substring(timestamp.indexOf("T") + 1);
		return timestamp;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar(String date, String time) {
		if (date == null || time == null) {
			throw new EtreeCommonsException("", "One of the parameters (date / time ) is null.");
		}
		String datetime = date + "T" + time;
		XMLGregorianCalendar xmlGregorianCalendar;
		try {
			xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(datetime);
		} catch (DatatypeConfigurationException e) {
			throw new EtreeCommonsException("", e);
		}
		return xmlGregorianCalendar;
	}

	public static XMLGregorianCalendar createXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar,
			String time) {
		if (xmlGregorianCalendar == null) {
			return null;
		}
		XMLGregorianCalendar xmlCal = null;
		try {
			String timeStamp = xmlGregorianCalendar.toString();
			if (time != null) {
				if (time.length() < 6) {
					time = time + ":00";
				}
				int idx = timeStamp.indexOf("T");
				if (idx > 0) {
					timeStamp = timeStamp.substring(0, idx);
				}
				timeStamp = timeStamp + "T" + time;
				xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(timeStamp);
			}
		} catch (Exception ex) {
			throw new EtreeCommonsException("", ex);
		}
		return xmlCal;
	}

}
