/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-commons.
* 
*  The etree-commons is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The etree-commons is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with etree-commons project.  If not, see <https://www.gnu.org/licenses/>.
*
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
