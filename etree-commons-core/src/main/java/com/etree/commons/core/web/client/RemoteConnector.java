/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core.web.client;

import com.etree.commons.core.dto.RequestDto;

public interface RemoteConnector {
	
	enum HttpMethods { PUT, GET, POST };
	
//	void registerMe();
	
	public <T> T callRemote(RequestDto requestDto);
}
