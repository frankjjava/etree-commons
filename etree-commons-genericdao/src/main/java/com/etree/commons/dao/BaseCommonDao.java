/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.dao;


public interface BaseCommonDao {

	long NANOS_IN_ONE_MILLI = 1000000;

	String COLUMN_CREATED_TIMESTAMP = "CREATED_TIMESTAMP";
	String COLUMN_CREATED_BY = "CREATED_BY";
	String COLUMN_UPDATED_TIMESTAMP = "UPDATED_TIMESTAMP";
	String COLUMN_UPDATED_BY = "UPDATED_BY";
	String COLUMN_STATUS = "STATUS";
	
	public enum ColumnStatuses {
		STATUS_NEW("New"),
		STATUS_HOLD("Hold"),
		STATUS_ACTIVE("Active"),
		STATUS_INACTIVE("Inactive");
		
		private String value;
		
		ColumnStatuses(String statusValue) {
			this.value = statusValue;
		}
	}
}
