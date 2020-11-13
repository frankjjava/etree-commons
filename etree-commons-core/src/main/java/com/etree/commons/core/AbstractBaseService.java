/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.etree.commons.core.dto.EtreeRequestContext;
import com.etree.commons.core.dto.IdentityDto;
import com.etree.commons.core.exception.EtreeCommonsException;


public abstract class AbstractBaseService extends AbstractBase implements BaseService {

	private Logger LOGGER = LoggerFactory.getLogger(AbstractBaseService.class);
	protected ThreadPoolTaskExecutor threadPoolExecutor;

	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	protected <T> T  process(BaseService baseService, Object request, EtreeRequestContext requestDto) {
		requestDto.setRequest(request);
		return baseService.fetchData(requestDto);
	}
			
	protected boolean isUserAuthorized(EtreeRequestContext requestDto) {
		//TODO - remove / comment below if (true) ... block before deploying to prod 
//		if (true) {
//			return true;
//		}
		IdentityDto identityDto = requestDto.getIdentityDto();
		if (identityDto == null) {
			throw new AccessDeniedException("Access Denied! User not authenticated!");
		}
		String serviceAndResource = createFullyQualifiedService(requestDto);
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) identityDto.getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			if (serviceAndResource.equalsIgnoreCase(authority.getAuthority())) {
				hasRole = true;
				break;
			}
		}
		if (!hasRole) {
			throw new AccessDeniedException(new StringBuilder("Access Denied! ").append(identityDto.getUserFullName())
				.append(" is not authorized to call service: ").append(serviceAndResource).toString());
		}
		return hasRole;
	}
	
	protected String createFullyQualifiedService(EtreeRequestContext requestDto) {
		String service = requestDto.getService();
		String resource = requestDto.getResourceOnly();
		if (resource != null) {
			service = new StringBuilder(service).append(".").append(resource).toString();
		}
		return service;
	}

	protected Map<String, String> convertQueryStringToMap(EtreeRequestContext requestDto) {
		Map<String, String> mapQueryParams = null;
		String resource = requestDto.getResource();
		if (resource != null && resource.contains("?")) {
			mapQueryParams = new HashMap<>();
			resource = resource.substring(resource.indexOf("?") + 1);
			String[] queryParams = resource.split("&");
			for (String queryParam : queryParams) {
				String[] keyValue = queryParam.split("=");
				mapQueryParams.put(keyValue[0], keyValue[1]);
			}
		}
		return mapQueryParams;
	}

	protected Future<?> doAsyncCall(Object id, Map<Object, Future<?>> mapFutures, Callable<?> callable) {
		Future<?> future = null; 
		try {
			future = threadPoolExecutor.submit(callable);
			if (mapFutures != null && id != null) {
				mapFutures.put(id, future);
			}
		} catch (EtreeCommonsException e) {
			LOGGER.warn(e.getMessage());
		}
		return future;
	}
	
	protected Map<Object, Object> waitForAsyncCallsToComplete(Map<Object, Future<?>> mapFutures, 
			int responsePollingPauseDurationInMillis) {
		boolean isStillRunning = true;
		Set<Object> processed = new HashSet<>();
//		long startTimeMillis = System.currentTimeMillis();
		Map<Object, Object> mapResponses = null;
		while (isStillRunning) {
			try {
//				if (!isStillRunning) {
//					break;
//				}
				Thread.sleep(responsePollingPauseDurationInMillis);
			} catch (InterruptedException e) {
				LOGGER.warn(e.getMessage());
			}
			isStillRunning = false;
			for (Entry<Object, Future<?>> entry : mapFutures.entrySet()) {
				Object thirdPartyId = entry.getKey();
				if (processed.contains(thirdPartyId)) {
					continue;
				}
				Future<?> future = entry.getValue();
				if (future.isDone()) {
					try {
						Object result = future.get();
						if (mapResponses == null) {
							mapResponses = new HashMap<>();
						}
						mapResponses.put(thirdPartyId, result);
						processed.add(thirdPartyId);
					} catch (InterruptedException | ExecutionException e) {
						LOGGER.warn("", e);
						if (e instanceof ExecutionException) {
							processed.add(thirdPartyId);
							future.cancel(true);
						}
					} catch (Exception e) {
						processed.add(thirdPartyId);
						future.cancel(true);
					}
//				} else if (!future.isCancelled()){
//					boolean timeoutEnabled = configurationWrapper.getBoolean(TIMEOUT_ENABLED, responsePollingTimeoutEnabled);
//					long currentTimeMillis = System.currentTimeMillis();
//					if (timeoutEnabled && (currentTimeMillis - startTimeMillis) > 
//								configurationWrapper.getLong(TIMEOUT_DURATION, responsePollingTimeoutDurationMillis)) {
//						future.cancel(true);
//						String msg = new StringBuilder("Timedout! Cancelled Task of '").append(thirdPartyId).append("'")
//								.toString();
//						LOGGER.error(msg);
//						Object result = NdcUtilFacade.buildResponseWithError(requestDto, msg);
//						lstResponses.add(result);
//					} else {
//						isStillRunning = true; 
//					}
				} else {
					isStillRunning = true; 
				}
			}
//			try {
////				if (!isStillRunning) {
////					break;
////				}
//				Thread.sleep(responsePollingPauseDurationInMillis);
//			} catch (InterruptedException e) {
//				LOGGER.warn(e.getMessage());
//			}
		}
		return mapResponses;
	}

}

 