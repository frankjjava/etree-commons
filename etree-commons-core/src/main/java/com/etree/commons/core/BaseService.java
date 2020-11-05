/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import com.etree.commons.core.dto.RequestDto;

public interface BaseService {

	double NANOS_IN_ONE_MILLI = 1000000;

	String SERVICE = "Service";
	
	String SERVICE_SWITCH_OFFICE = "SwitchOffice";
	
	String SERVICE_IDENTITY_USEREXISTS		= "Identity.userExists";
	String SERVICE_IDENTITY_PASSWORD		= "Identity.password";
	String SERVICE_IDENTITY_USERDETAILS		= "Identity.userDetails";
	String SERVICE_IDENTITY_ACCESSRIGHTS	= "Identity.accessRights";
	String SERVICE_IDENTITY_CONFIGUREDROLE	= "Identity.configuredRole";
	String SERVICE_IDENTITY_CONFIGUREDROLES	= "Identity.configuredRoles";

	String SERVICE_CONFIGURATOR				= "Configurator";
	String SERVICE_IDENTITY				= "Identity";
	
	<T> T fetchData(RequestDto requestWrapper);
}
