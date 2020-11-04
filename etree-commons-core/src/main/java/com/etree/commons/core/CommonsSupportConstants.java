/**
* Copyright © 2020 Franklinton IT Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.core;

public interface CommonsSupportConstants {

	public enum COLLECTION_TYPE {MAP, LIST};
	
	public enum DATE_FORMAT {
		YYYY_MM_DD("yyyy-MM-dd"),
		YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
		YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
		YYYY_MM_DD_HH_MM_SS_SSS_Z("yyyy-MM-dd HH:mm:ss.SSS Z"),
		YYYY_MM_DD_HH_MM_SS_SSS_z("yyyy-MM-dd HH:mm:ss.SSS z"),
		YYYY_MM_DDTHH_MM_SS_SSS("yyyy-MM-dd'T'HH:mm:ss.SSS"),
		YYYY_MM_DDTHH_MM_SS_SSS_Z("yyyy-MM-dd'T'HH:mm:ss.SSS Z"),
		YYYY_MM_DDTHH_MM_SS_SSS_z("yyyy-MM-dd'T'HH:mm:ss.SSS z"),
		DD_MMMM_YYYY_HH_MM_SS_z("d MMMM y h:mm:ss z"),
		YYYY_MM_DDTHH_MM_SSZ("yyyy-MM-dd'T'HH:mm:ss'Z'"),;

		private String value;
		
		DATE_FORMAT(String value) {
			this.value = value;
		}
		
		public String value() {
			return value;
		}
	}
	String CLASSPATH = "classpath:";
}
