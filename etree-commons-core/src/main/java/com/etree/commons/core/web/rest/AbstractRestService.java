/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core.web.rest;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.etree.commons.core.AbstractBaseService;
import com.etree.commons.core.BaseService;
import com.etree.commons.core.dto.EtreeRequestContext;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.commons.core.utils.CommonUtils;
import com.etree.commons.core.utils.TxIdUtil;

public abstract class AbstractRestService extends AbstractBaseService implements RestService {

	private Logger LOGGER = LoggerFactory.getLogger(AbstractRestService.class);

	protected <T> T callService(BaseService baseService, EtreeRequestContext requestDto, Logger logger) {
		return callService(baseService, requestDto, logger, false);
	}
	
	protected <T> T callService(BaseService baseService, EtreeRequestContext requestDto, Logger logger, boolean bypassSecurity) {
		if (logger == null) {
			logger = LOGGER;
		}
		logEntryOrExit(requestDto, logger, true);
		Object response = null;
		try {
			if (!bypassSecurity) {
				boolean hasRole = isUserAuthorized(requestDto);
			}
			if (HttpMethod.POST.equalsIgnoreCase(requestDto.getActionType())) {
				Object request = requestDto.getRequest();
				if (request == null) {
					Class<?> requestType = requestDto.getRequestType();
					String msg = "Invalid request type! Expected '" + requestType.getName() + "' but received null.";
					logger.error(msg);
					throw new EtreeCommonsException("", msg); 
				} 
				if (request instanceof String || request instanceof LinkedHashMap) {
					request = convertJsonToPojo(requestDto);
					requestDto.setRequest(request);
				}
			}
			response = baseService.fetchData(requestDto);
		} catch (Exception	ex) {
			logger.error(ExceptionUtils.getStackTrace(ex));
			response = CommonUtils.createMessageDto(ex); 
		}
		logEntryOrExit(requestDto, logger, false);
		return (T) response;
	}
	
	protected void logEntryOrExit(EtreeRequestContext requestDto, Logger logger, boolean isEntry) {
		String serviceId = requestDto.getService();
		String resource = requestDto.getResource();
		if (resource != null) {
			serviceId.concat(".").concat(resource);
		}
		String host = requestDto.getRemoteHost();
		if (logger == null) {
			logger = LOGGER;
		}
		if (isEntry) {
			logger.info(new StringBuilder("Received '").append(serviceId).append("' request from ").append(host)
					.append(". TxId=").append(requestDto.getTransactionId()).toString());
		} else {
			logger.info(new StringBuilder("Sending '").append(serviceId).append("' response to ").append(host)
					.append(". TxId=").append(requestDto.getTransactionId()).toString());
		}
	}

	protected void init(HttpServletRequest httpRequest, EtreeRequestContext requestDto) {
		if (requestDto.getActionType() == null) {
			String actionType = httpRequest.getHeader(EtreeRequestContext.ACTION_TYPE);
			if (actionType == null) {
				actionType = httpRequest.getMethod();
			}
			requestDto.setActionType(actionType);
		}		
		if (requestDto.getTransactionId() == null) {
			String txId = TxIdUtil.createUidTokenWithMillis(20);
			requestDto.setTransactionId(txId);
		}
	}
}
