package com.etree.commons.core.utils;

import com.etree.commons.core.dto.RequestDto;
import com.etree.commons.core.web.dto.UserDetailsDto;

public class RequestWrapperUtil {

	public static String getResourceOnly(RequestDto requestWrapper) {
		return getResourceWithoutQueryString(requestWrapper.getResource());
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
	
	public static String getQueryString(RequestDto requestWrapper) {
		String resource = requestWrapper.getResource();
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
	
	public static String getUserId(RequestDto requestWrapper) {
		UserDetailsDto userDetailsDto = requestWrapper.getUserDetails();
		return getUserId(userDetailsDto);
	}

	public static String getUserId(UserDetailsDto userDetailsDto) {
		String userId = null;
		if (userDetailsDto != null) {
			userId = userDetailsDto.getUsername();
		}
		return userId;
	}
}
