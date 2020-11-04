/**
* Copyright © 2020 Franklinton IT Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
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
