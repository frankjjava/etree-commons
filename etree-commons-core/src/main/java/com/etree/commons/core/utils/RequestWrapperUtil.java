package com.etree.commons.core.utils;

import com.etree.commons.core.dto.RequestDto;

public class RequestWrapperUtil {

	public static String getResourceOnly(RequestDto requestDto) {
		return getResourceWithoutQueryString(requestDto.getResource());
	}
	
	public static String getResourceWithoutQueryString(String resource) {
		if (resource == null) {
			return null;
		}
		resource = resource.trim();
		int loc = resource.indexOf("?");
		if (loc == 0) {
			return null;
		} else if (loc > 0) {
			resource = resource.substring(0, loc);
			if (resource.endsWith("/")) {
				resource = resource.substring(0, resource.lastIndexOf("/"));
			}
		}
		return resource;
	}
	
	public static String getQueryString(RequestDto requestDto) {
		String resource = requestDto.getResource();
		if (resource == null) {
			return null;
		}
		resource = resource.trim();
		int loc = resource.indexOf("?");
		if (loc < 0) {
			return null;
		} 
		resource = resource.substring(loc);
		if (resource.startsWith("/")) {
			resource = resource.substring(1);
		}
		return resource;
	}

}
