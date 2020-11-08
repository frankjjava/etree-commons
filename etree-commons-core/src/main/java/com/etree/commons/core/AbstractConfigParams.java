/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.PropertyConverter;

public abstract class AbstractConfigParams implements ConfigParams {

	protected Map<Object, Object> configParams;
	public Boolean doRemoteCall = true;
	
	@Override
	public void setConfigParams(Map<Object, Object> params) {
		this.configParams = params;
	}

	@Override
	public Map<Object, Object> getConfigParams() {
		return configParams;
	}

	@Override
	public void putConfigValue(Object key, Object value) {
		if (configParams == null) {
			configParams = new HashMap<>();
		}
		configParams.put(key, value);
	}

	@Override
	public Object getObject(Object key) {
		if (configParams == null) {
			return null;
		}
		return configParams.get(key);
	}

	@Override
	public String getString(Object key) {
		Object value = getObject(key);
		String strValue = null; 
		if (value != null && value instanceof String) {
			strValue = (String) value;
		}
		return strValue;
	}

	@Override
	public Integer getInteger(Object key) {
		Object value = getObject(key);
		Integer integer = null; 
		if (value != null) {
			integer = PropertyConverter.toInteger(value);
		}
		return integer;
	}

	@Override
	public Boolean getBoolean(Object key) {
		Object value = getObject(key);
		Boolean bool = null; 
		if (value != null) {
			bool = PropertyConverter.toBoolean(value);
		}
		return bool;
	}

	@Override
	public Float getFloat(Object key) {
		Object value = getObject(key);
		Float fltValue = null; 
		if (value != null) {
			fltValue = PropertyConverter.toFloat(value);
		}
		return fltValue;
	}

}
