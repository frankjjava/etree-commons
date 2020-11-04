/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core.web.client;

import com.etree.commons.core.dto.RequestWrapperDto;

public interface RemoteConnector {
	
	enum HttpMethods { PUT, GET, POST };
	
//	void registerMe();
	
	public <T> T doRemoteCall(RequestWrapperDto requestWrapper);
}
