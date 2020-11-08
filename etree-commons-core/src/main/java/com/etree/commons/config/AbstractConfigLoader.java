/**
* Copyright (c) elastictreetech.com
*
* @author  Franklin Abel
* @version 1.0
* @since   2019-01-06 
*/
package com.etree.commons.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etree.commons.core.exception.EtreeCommonsException;
import com.etree.commons.core.utils.CommonUtils;

@Configuration
public abstract class AbstractConfigLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigLoader.class);

	private static final String ETREE_HOME_ENV_VAR = "ETREE_HOME";
	
	protected abstract String getDbmsDriver();
	protected abstract String getDbms();
	protected abstract String getDbmsHost();
	protected abstract String getDbmsPort();
	protected abstract String getDbmsDbname();
	protected abstract String getDbmsUser();
	protected abstract String getDbmsPwd();
	
	@Bean
	public BasicDataSource createBasicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(getDbmsDriver());
		String url = "jdbc:%s://%s:%s/%s";
		url = String.format(url, getDbms(), getDbmsHost(), getDbmsPort(), getDbmsDbname());		
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(getDbmsUser());
		basicDataSource.setPassword(getDbmsPwd());
		return basicDataSource;
	}

	public static Properties load(String serviceId) {
		Map<String, String> sysEnv = System.getenv();
		if (!sysEnv.containsKey(ETREE_HOME_ENV_VAR)) {
			throw new EtreeCommonsException("","Oops... Cannot proceed - 'etree_home' not set! "
					+ "Please set etree_home environment variable.");
		}
		String etreeHome = sysEnv.get(ETREE_HOME_ENV_VAR);
		if (CommonUtils.isEmpty(etreeHome)) {
			throw new EtreeCommonsException("","Oops... Cannot proceed - 'etree_home' not set! "
					+ "Please set etree_home environment variable.");
		}
		Properties configProps = new Properties();
        try (InputStream inStream = new FileInputStream(etreeHome + "/" + serviceId  + "/config"+ "/app.properties")) {
        	configProps.load(inStream);
        } catch (IOException ex) {
        	LOGGER.error(ex.getMessage());
        	throw new EtreeCommonsException(ex);
        }
        return configProps;
	}
	
	public static String getConfigValue(String serviceId, String key) {
		// Loading inside the method is done deliberately in order to ensure the loaded properties which has 
		// sensitive information does not stay in the memory and becomes eligible for garbage collection instantly.
		Properties props = AbstractConfigLoader.load(serviceId);
		if (props.containsKey(key)) {
			return props.getProperty(key);
		}
		return null;
	}

}
