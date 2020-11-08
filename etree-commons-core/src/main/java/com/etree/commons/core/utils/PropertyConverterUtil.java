/**
* Copyright (c) elastictreetech.com
*
* @author  Franklin Abel
* @version 1.0
* @since   2019-01-06 
*/
package com.etree.commons.core.utils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.etree.commons.core.exception.EtreeCommonsException;

public class PropertyConverterUtil {

    private static final String HEX_PREFIX = "0x";
    private static final int HEX_RADIX = 16;
    private static final String BIN_PREFIX = "0b";
    private static final int BIN_RADIX = 2;
    private static final Class<?>[] CONSTR_ARGS = {String.class};
    private static final char LIST_ESC_CHAR = '\\';
    private static final String INTERNET_ADDRESS_CLASSNAME = "javax.mail.internet.InternetAddress";
	private static final Map<Class<?>, Class<?>> wrapperTypes = new IdentityHashMap<>(8);

	static {
		wrapperTypes.put(Boolean.class, boolean.class);
		wrapperTypes.put(Byte.class, byte.class);
		wrapperTypes.put(Character.class, char.class);
		wrapperTypes.put(Double.class, double.class);
		wrapperTypes.put(Float.class, float.class);
		wrapperTypes.put(Integer.class, int.class);
		wrapperTypes.put(Long.class, long.class);
		wrapperTypes.put(Short.class, short.class);
		wrapperTypes.put(Void.class, void.class);
	}

	public static String toString(Object value) {
		if (value == null) {
			return null;
		}
		String strVal = null;
		Class<?> valType = value.getClass();
		if (String.class.equals(valType)) {
			strVal = (String) value;
		} else {
			boolean isPrimitiveOrWrapped = valType.isPrimitive() || wrapperTypes.containsKey(valType);
			if (isPrimitiveOrWrapped) {
				strVal = String.valueOf(value);
			} else if (value instanceof BigInteger) {
				strVal = ((BigInteger) value).toString();
			} else if (value instanceof BigDecimal) {
				strVal = ((BigDecimal) value).toString();
			} else {
				strVal = value.toString();
			}
		}
		return strVal;
	}
	
	public static Object convertTo(Object value, Class<?> clz) {
		if (clz == null) {
			return value;
		}
		Class<?> valType = value.getClass();
		if (clz == valType) {
			return value;
		} else if (clz.isAssignableFrom(valType)) {
			return clz.cast(value);
		}
		if (String.class.equals(clz)) {
			value = toString(value);
		} else if (Boolean.class.equals(clz) || Boolean.TYPE.equals(clz)) {
			value = toBoolean(value);
        } else if (Number.class.isAssignableFrom(clz) || clz.isPrimitive()) {
            if (Integer.class.equals(clz) || Integer.TYPE.equals(clz)) {
            	value = toInteger(value);
            } else if (Long.class.equals(clz) || Long.TYPE.equals(clz)) {
            	value = toLong(value);
            } else if (Byte.class.equals(clz) || Byte.TYPE.equals(clz)) {
            	value = toByte(value);
            } else if (Short.class.equals(clz) || Short.TYPE.equals(clz)) {
            	value = toShort(value);
            } else if (Float.class.equals(clz) || Float.TYPE.equals(clz)) {
            	value = toFloat(value);
            } else if (Double.class.equals(clz) || Double.TYPE.equals(clz)) {
            	value = toDouble(value);
            } else if (BigInteger.class.equals(clz)) {
            	value = toBigInteger(value);
            } else if (BigDecimal.class.equals(clz)) {
            	value = toBigDecimal(value);
            }
        } else if (Date.class.equals(clz)) {
//        	value = toDate(value, null);
        	//TODO - default date
        } else if (Calendar.class.equals(clz)) {
//        	value = toCalendar(value, null);
        	//TODO - default date
        } else if (URL.class.equals(clz)) {
        	value = toURL(value);
        } else if (Locale.class.equals(clz)) {
            return toLocale(value);
        } else if (isEnum(clz)) {
            return convertToEnum(clz, value);
        } else if (clz.getName().equals(INTERNET_ADDRESS_CLASSNAME)) {
            return toInternetAddress(value);
        } else if (InetAddress.class.isAssignableFrom(clz)) {
            return toInetAddress(value);
        } else {
	        throw new EtreeCommonsException("The value '" + value + "' (" + value.getClass() + ")"
	                + " couldn't be converted to a " + clz.getName() + " object");
        }
		return value;
    }

	public static Boolean toBoolean(Object value) {
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof String) {
			Boolean bool = toBooleanObject((String) value);
			if (bool == null) {
				throw new EtreeCommonsException("Boolean Convertion error! The value " + value + 
						" couldn't be converted.");
			}
			return bool;
		} else {
			throw new EtreeCommonsException("Boolean Convertion error! The value " + value + 
					" couldn't be converted.");
		}
	}

	public static Byte toByte(Object value) {
		Number num = toNumber(value, Byte.class);
		if (num instanceof Byte) {
			return (Byte) num;
		} else {
			return new Byte(num.byteValue());
		}
	}

	public static Short toShort(Object value) {
		Number num = toNumber(value, Short.class);
		if (num instanceof Short) {
			return (Short) num;
		} else {
			return new Short(num.shortValue());
		}
	}

	public static Integer toInteger(Object value) {
		Number num = toNumber(value, Integer.class);
		if (num instanceof Integer) {
			return (Integer) num;
		} else {
			return new Integer(num.intValue());
		}
	}

	public static Long toLong(Object value) {
		Number num = toNumber(value, Long.class);
		if (num instanceof Long) {
			return (Long) num;
		} else {
			return new Long(num.longValue());
		}
	}

	public static Float toFloat(Object value) {
		Number num = toNumber(value, Float.class);
		if (num instanceof Float) {
			return (Float) num;
		} else {
			return new Float(num.floatValue());
		}
	}

	public static Double toDouble(Object value) {
		Number num = toNumber(value, Double.class);
		if (num instanceof Double) {
			return (Double) num;
		} else {
			return new Double(num.doubleValue());
		}
	}

	public static BigInteger toBigInteger(Object value) {
		Number num = toNumber(value, BigInteger.class);
		if (num instanceof BigInteger) {
			return (BigInteger) num;
		} else {
			return BigInteger.valueOf(num.longValue());
		}
	}

	public static BigDecimal toBigDecimal(Object value) {
		Number num = toNumber(value, BigDecimal.class);
		if (num instanceof BigDecimal) {
			return (BigDecimal) num;
		} else {
			return new BigDecimal(num.doubleValue());
		}
	}

	static Number toNumber(Object value, Class<?> clz) {
		if (value == null) {
			return null;
		}
		if (value instanceof Number) {
			return (Number) value;
		}
		String strValue = value.toString();
		try {
			if (strValue.startsWith(HEX_PREFIX)) {
				return new BigInteger(strValue.substring(HEX_PREFIX.length()), HEX_RADIX);
			} else if (strValue.startsWith(BIN_PREFIX)) {
				return new BigInteger(strValue.substring(BIN_PREFIX.length()), BIN_RADIX);
			} else {
				Constructor<?> constr = clz.getConstructor(CONSTR_ARGS);
				return (Number) constr.newInstance(new Object[] { strValue });
			}
		} catch (NumberFormatException nex) {
			String errMsg = null;
			if (strValue.startsWith(HEX_PREFIX)) {
				errMsg = "! Invalid hex number.";
			} else if (strValue.startsWith(BIN_PREFIX)) {
				errMsg = "! Invalid binary number.";
			}
			throw new EtreeCommonsException("Number Convertion error! Error converting '" + strValue + "' to " + 
					clz.getName() + errMsg, nex);
		} catch (Exception ex) {
			throw new EtreeCommonsException("Number Convertion error! Error converting '" + strValue + "' to " + 
					clz.getName(), ex);
		}
	}

	public static URL toURL(Object value) {
		if (value instanceof URL) {
			return (URL) value;
		} else if (value instanceof String) {
			try {
				return new URL((String) value);
			} catch (MalformedURLException e) {
				throw new EtreeCommonsException("URL Convertion error! Error converting '" + value + 
						"' couldn't be converted.", e);
			}
		} else {
			throw new EtreeCommonsException("URL Convertion error! Error converting '" + value + 
					"' couldn't be converted.");
		}
	}

	public static Locale toLocale(Object value) {
		if (value instanceof Locale) {
			return (Locale) value;
		} else if (value instanceof String) {
			List<String> elements = split((String) value, '_');
			int size = elements.size();

			if (size >= 1 && ((elements.get(0)).length() == 2 || (elements.get(0)).length() == 0)) {
				String language = elements.get(0);
				String country = (size >= 2) ? elements.get(1) : "";
				String variant = (size >= 3) ? elements.get(2) : "";

				return new Locale(language, country, variant);
			} else {
				throw new EtreeCommonsException("Locale Convertion error! The value " + value + 
						" couldn't be converted.");
			}
		} else {
			throw new EtreeCommonsException("Locale Convertion error! The value " + value + 
					" couldn't be converted.");
		}
	}

	public static List<String> split(String s, char delimiter, boolean trim) {
		if (s == null) {
			return new ArrayList<String>();
		}
		List<String> list = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		int begin = 0;
		boolean inEscape = false;
		while (begin < s.length()) {
			char c = s.charAt(begin);
			if (inEscape) {
				if (c != delimiter && c != LIST_ESC_CHAR) {
					// no, also add escape character
					token.append(LIST_ESC_CHAR);
				}
				token.append(c);
				inEscape = false;
			} else {
				if (c == delimiter) {
					// found a list delimiter -> add token and resetDefaultFileSystem buffer
					String t = token.toString();
					if (trim) {
						t = t.trim();
					}
					list.add(t);
					token = new StringBuilder();
				} else if (c == LIST_ESC_CHAR) {
					// eventually escape next character
					inEscape = true;
				} else {
					token.append(c);
				}
			}
			begin++;
		}
		// Trailing delimiter?
		if (inEscape) {
			token.append(LIST_ESC_CHAR);
		}
		// Add last token
		String t = token.toString();
		if (trim) {
			t = t.trim();
		}
		list.add(t);
		return list;
	}

	public static List<String> split(String s, char delimiter) {
		return split(s, delimiter, true);
	}

	static InetAddress toInetAddress(Object value) {
		if (value instanceof InetAddress) {
			return (InetAddress) value;
		} else if (value instanceof String) {
			try {
				return InetAddress.getByName((String) value);
			} catch (UnknownHostException e) {
				throw new EtreeCommonsException("InetAddress Convertion error! The value " + value + " couldn't be converted.", e);
			}
		} else {
			throw new EtreeCommonsException("InetAddress Convertion error! The value " + value + " couldn't be converted.");
		}
	}

	static Object toInternetAddress(Object value) {
		if (value.getClass().getName().equals(INTERNET_ADDRESS_CLASSNAME)) {
			return value;
		} else if (value instanceof String) {
			try {
				Constructor<?> ctor = Class.forName(INTERNET_ADDRESS_CLASSNAME)
						.getConstructor(new Class[] { String.class });
				return ctor.newInstance(new Object[] { value });
			} catch (Exception e) {
				throw new EtreeCommonsException("InetAddress Convertion error! The value " + value + 
						" couldn't be converted.", e);
			}
		} else {
			throw new EtreeCommonsException("InetAddress Convertion error! The value " + value + " "
					+ "couldn't be converted.");
		}
	}

	static boolean isEnum(Class<?> cls) {
		return cls.isEnum();
	}

	static <E extends Enum<E>> E toEnum(Object value, Class<E> cls) {
		if (value.getClass().equals(cls)) {
			return cls.cast(value);
		} else if (value instanceof String) {
			try {
				return Enum.valueOf(cls, (String) value);
			} catch (Exception e) {
				throw new EtreeCommonsException("Enum Convertion error! The value " + value + " couldn't be converted to a " + 
						cls.getName());
			}
		} else if (value instanceof Number) {
			try {
				E[] enumConstants = cls.getEnumConstants();
				return enumConstants[((Number) value).intValue()];
			} catch (Exception e) {
				throw new EtreeCommonsException("Enum Convertion error! The value " + value + " couldn't be converted to a " + 
						cls.getName());
			}
		} else {
			throw new EtreeCommonsException("Enum Convertion error! The value " + value + " couldn't be converted to a " + 
					cls.getName());
		}
	}

	public static Date toDate(Object value, String format) {
		if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof Calendar) {
			return ((Calendar) value).getTime();
		} else if (value instanceof String) {
			try {
				return new SimpleDateFormat(format).parse((String) value);
			} catch (ParseException e) {
				throw new EtreeCommonsException("Date Convertion error! The value " + value + 
						" couldn't be converted.", e);
			}
		} else {
			throw new EtreeCommonsException("Date Convertion error! The value " + value + " couldn't be converted.");
		}
	}

	public static Calendar toCalendar(Object value, String format) {
		if (value instanceof Calendar) {
			return (Calendar) value;
		} else if (value instanceof Date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) value);
			return calendar;
		} else if (value instanceof String) {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new SimpleDateFormat(format).parse((String) value));
				return calendar;
			} catch (ParseException e) {
				throw new EtreeCommonsException("Calendar Convertion error! The value " + value + " couldn't be converted.", e);
			}
		} else {
			throw new EtreeCommonsException("Calendar Convertion error! The value " + value + " couldn't be converted.");
		}
	}

	@SuppressWarnings("unchecked")
	private static Object convertToEnum(Class<?> enumClass, Object value) {
		return toEnum(value, enumClass.asSubclass(Enum.class));
	}

	public static Boolean toBooleanObject(String str) {
		// Previously used equalsIgnoreCase, which was fast for interned 'true'.
		// Non interned 'true' matched 15 times slower.
		// Optimization provides same performance as before for interned 'true'.
		// Similar performance for null, 'false', and other strings not length 2/3/4.
		// 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower.
		if (str == "true") {
			return Boolean.TRUE;
		}
		if (str == null) {
			return null;
		}
		switch (str.length()) {
			case 1: {
				char ch0 = str.charAt(0);
				if ((ch0 == 'y' || ch0 == 'Y') || (ch0 == 't' || ch0 == 'T')) {
					return Boolean.TRUE;
				}
				if ((ch0 == 'n' || ch0 == 'N') || (ch0 == 'f' || ch0 == 'F')) {
					return Boolean.FALSE;
				}
				break;
			}
			case 2: {
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N')) {
					return Boolean.TRUE;
				}
				if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O')) {
					return Boolean.FALSE;
				}
				break;
			}
			case 3: {
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				char ch2 = str.charAt(2);
				if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S')) {
					return Boolean.TRUE;
				}
				if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F')) {
					return Boolean.FALSE;
				}
				break;
			}
			case 4: {
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				char ch2 = str.charAt(2);
				char ch3 = str.charAt(3);
				if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U')
						&& (ch3 == 'e' || ch3 == 'E')) {
					return Boolean.TRUE;
				}
				break;
			}
			case 5: {
				char ch0 = str.charAt(0);
				char ch1 = str.charAt(1);
				char ch2 = str.charAt(2);
				char ch3 = str.charAt(3);
				char ch4 = str.charAt(4);
				if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L')
						&& (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E')) {
					return Boolean.FALSE;
				}
				break;
			}
		}
		return null;
	}
}
