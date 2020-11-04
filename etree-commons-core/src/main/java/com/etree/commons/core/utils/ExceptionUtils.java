package com.etree.commons.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils extends org.apache.commons.lang.exception.ExceptionUtils {
  
    public static String getPartialStackTrace(Throwable throwable, int maxLines) {
    	StringWriter writer = new StringWriter();
    	throwable.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(lines.length, maxLines); i++) {
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }
}
