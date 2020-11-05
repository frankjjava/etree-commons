/**
 * Copyright © 2020 eTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Joshua
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.commons.core.dto;

import java.sql.Timestamp;

import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.web.dto.UserDetailsDto;

public class RequestDto implements Cloneable { 

	public static final String REQUEST_WRAPPER_DTO = "RequestDto";
	public static final String THIRDPARTY_ID = "ThirdpartyId";
	public static final String RESOURCE = "Resource";
	public static final String ACTION_TYPE = "ActionType";

	public static final String POST = "post";
	public static final String DELETE = "delete";
	public static final String PUT = "put";
	public static final String READ = "get";
	public static final String GET = "get";
	public static final String STATUS = "approve";
		
	private String transactionId;
	private String remoteAddr;
	private String remoteHost;
	private int remotePort;
	private String actionType;
	private String service;
	private String version;
	private String resource;
	private String resourceOnly;
	private String serviceWithResourceOnly;
	private String contentType;
	private Object request;
	private Timestamp requestedTimestamp;
	private Class<?> requestType;
	private Class<?> responseType;
	private CommonsSupportConstants.COLLECTION_TYPE collectionType;
	private Object response;
	private Timestamp responseTimestamp;
	private UserDetailsDto userDetails;
	private String serverHost;
	private int serverPort;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getRemoteHost() {
		return remoteHost;
	}
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getResourceOnly() {
		return resourceOnly;
	}
	public void setResourceOnly(String resourceWithoutQueryString) {
		this.resourceOnly = resourceWithoutQueryString;
	}
	public String getServiceWithResourceOnly() {
		return serviceWithResourceOnly;
	}
	public void setServiceWithResourceOnly(String serviceWithResourceOnly) {
		this.serviceWithResourceOnly = serviceWithResourceOnly;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Object getRequest() {
		return request;
	}
	public void setRequest(Object request) {
		this.request = request;
	}
	public Timestamp getRequestedTimestamp() {
		return requestedTimestamp;
	}
	public void setRequestedTimestamp(Timestamp requestedTime) {
		this.requestedTimestamp = requestedTime;
	}
	public Class<?> getRequestType() {
		return requestType;
	}
	public void setRequestType(Class<?> requestType) {
		this.requestType = requestType;
	}
	public Class<?> getResponseType() {
		return responseType;
	}
	public void setResponseType(Class<?> responseType) {
		this.responseType = responseType;
	}
	public CommonsSupportConstants.COLLECTION_TYPE getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(CommonsSupportConstants.COLLECTION_TYPE collectionType) {
		this.collectionType = collectionType;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public Timestamp getResponseTimestamp() {
		return responseTimestamp;
	}
	public void setResponseTimestamp(Timestamp responseTime) {
		this.responseTimestamp = responseTime;
	}
	public UserDetailsDto getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetailsDto userDetails) {
		this.userDetails = userDetails;
	}

	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	@Override
	public RequestDto clone() {
		RequestDto requestWrapperDto = new RequestDto();
		requestWrapperDto.setTransactionId(transactionId);
		requestWrapperDto.setActionType(actionType);
		requestWrapperDto.setContentType(contentType);
		requestWrapperDto.setCollectionType(collectionType);
		requestWrapperDto.setRemoteAddr(remoteAddr);
		requestWrapperDto.setRemoteHost(remoteHost);
		requestWrapperDto.setRemotePort(remotePort);
		requestWrapperDto.setRequest(request);
		requestWrapperDto.setResourceOnly(resourceOnly);
		requestWrapperDto.setRequestedTimestamp(requestedTimestamp);
		requestWrapperDto.setRequestType(requestType);
		requestWrapperDto.setResource(resource);
		requestWrapperDto.setResponse(response);
		requestWrapperDto.setResponseTimestamp(responseTimestamp);
		requestWrapperDto.setResponseType(responseType);
		requestWrapperDto.setService(service);
		requestWrapperDto.setUserDetails(userDetails);
		requestWrapperDto.setVersion(version);
		requestWrapperDto.setServerHost(serverHost);
		requestWrapperDto.setServerPort(serverPort);
		return requestWrapperDto;
	}
}
