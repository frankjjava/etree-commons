/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import java.util.Map;

public interface ConfigParams {

	public void setConfigParams(Map<Object, Object> params);
	
	public Map<Object, Object> getConfigParams();
	
	public Object getObject(Object key);
	
	public String getString(Object key);
	
	public Integer getInteger(Object key);
	
	public Boolean getBoolean(Object key);
	
	public Float getFloat(Object key);
	
	public void putConfigValue(Object key, Object value);

}
