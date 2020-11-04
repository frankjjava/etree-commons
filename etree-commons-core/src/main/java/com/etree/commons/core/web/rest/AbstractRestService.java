/**
* Copyright © 2020 Franklinton IT Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core.web.rest;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.AbstractBaseService;
import com.etree.commons.core.BaseService;
import com.etree.commons.core.dto.RequestWrapperDto;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.commons.core.utils.CommonUtils;
import com.etree.commons.core.utils.TranscientUidUtil;

public abstract class AbstractRestService extends AbstractBaseService implements RestService {

	private Logger LOGGER = LoggerFactory.getLogger(AbstractRestService.class);

	protected <T> T callService(BaseService baseService, RequestWrapperDto requestWrapper, Logger logger) {
		return callService(baseService, requestWrapper, logger, false);
	}
	
	protected <T> T callService(BaseService baseService, RequestWrapperDto requestWrapper, Logger logger, boolean bypassSecurity) {
		if (logger == null) {
			logger = LOGGER;
		}
		logEntryOrExit(requestWrapper, logger, true);
		Object response = null;
		try {
			if (!bypassSecurity) {
				boolean hasRole = isUserAuthorized(requestWrapper);
			}
			if (HttpMethod.POST.equalsIgnoreCase(requestWrapper.getActionType())) {
				Object request = requestWrapper.getRequest();
				if (request == null) {
					Class<?> requestType = requestWrapper.getRequestType();
					String msg = "Invalid request type! Expected '" + requestType.getName() + "' but received null.";
					logger.error(msg);
					throw new EtreeCommonsException("", msg); 
				} 
				if (request instanceof String || request instanceof LinkedHashMap) {
					request = convertJsonToPojo(requestWrapper);
					requestWrapper.setRequest(request);
				}
			}
			response = baseService.process(requestWrapper);
		} catch (Exception	ex) {
			logger.error(ExceptionUtils.getFullStackTrace(ex));
			response = CommonUtils.createMessageDto(ex); 
		}
		logEntryOrExit(requestWrapper, logger, false);
		return (T) response;
	}
	
	protected void logEntryOrExit(RequestWrapperDto requestWrapperDto, Logger logger, boolean isEntry) {
		String serviceId = requestWrapperDto.getService();
		String resource = requestWrapperDto.getResource();
		if (resource != null) {
			serviceId.concat(".").concat(resource);
		}
		String host = requestWrapperDto.getRemoteHost();
		if (logger == null) {
			logger = LOGGER;
		}
		if (isEntry) {
			logger.info(new StringBuilder("Received '").append(serviceId).append("' request from ").append(host)
					.append(". TxId=").append(requestWrapperDto.getTransactionId()).toString());
		} else {
			logger.info(new StringBuilder("Sending '").append(serviceId).append("' response to ").append(host)
					.append(". TxId=").append(requestWrapperDto.getTransactionId()).toString());
		}
	}

	protected void init(HttpServletRequest httpRequest, RequestWrapperDto requestWrapperDto) {
		if (requestWrapperDto.getActionType() == null) {
			String actionType = httpRequest.getHeader(RequestWrapperDto.ACTION_TYPE);
			if (actionType == null) {
				actionType = httpRequest.getMethod();
			}
			requestWrapperDto.setActionType(actionType);
		}		
		if (requestWrapperDto.getTransactionId() == null) {
			String txId = TranscientUidUtil.createUidTokenWithMillis(20);
			requestWrapperDto.setTransactionId(txId);
		}
	}
}
