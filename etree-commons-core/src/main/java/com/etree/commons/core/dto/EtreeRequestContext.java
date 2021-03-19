/**
 * Copyright © 2020 elasticTree Technologies Pvt. Ltd.
 *
 * @author  Franklin Abel
 * @version 1.0
 * @since   2020-11-04 
 */
package com.etree.commons.core.dto;

import java.sql.Timestamp;

import com.etree.commons.core.CommonsSupportConstants;

import lombok.Data;

@Data
public class EtreeRequestContext implements Cloneable { 
		
	private String transactionId;
	private String sessionId;
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
	private Class<?> requestType;
	private Timestamp requestedTimestamp;
	private Object response;
	private Class<?> responseType;
	private Timestamp responseTimestamp;
	private String userId;
	private CommonsSupportConstants.COLLECTION_TYPE collectionType;
	private IdentityDto identityDto;
	
	@Override
	public EtreeRequestContext clone() {
		EtreeRequestContext requestDto = new EtreeRequestContext();
		requestDto.setTransactionId(transactionId);
		requestDto.setActionType(actionType);
		requestDto.setContentType(contentType);
		requestDto.setRemoteAddr(remoteAddr);
		requestDto.setRemoteHost(remoteHost);
		requestDto.setRemotePort(remotePort);
		requestDto.setRequest(request);
		requestDto.setResourceOnly(resourceOnly);
		requestDto.setRequestedTimestamp(requestedTimestamp);
		requestDto.setResource(resource);
		requestDto.setResponse(response);
		requestDto.setResponseTimestamp(responseTimestamp);
		requestDto.setService(service);
		requestDto.setUserId(userId);
		requestDto.setVersion(version);
		return requestDto;
	}
}
