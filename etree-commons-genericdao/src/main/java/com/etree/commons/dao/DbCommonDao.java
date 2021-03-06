/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.dao;


public interface DbCommonDao {

	long NANOS_IN_ONE_MILLI = 1000000;

	String COLUMN_STATUS = "STATUS";
	String COLUMN_REMOTE_ADDRESS = "remote_address";
	String COLUMN_REMOTE_HOST = "remote_host";
	String COLUMN_CREATED_TIMESTAMP = "created_timestamp";
	String COLUMN_CREATED_BY = "CREATED_BY";
	String COLUMN_UPDATED_TIMESTAMP = "updated_timestamp";
	String COLUMN_UPDATED_BY = "UPDATED_BY";

}
