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
	
	String SQL_SELECT_FROM = "SELECT * from ";
	String SQL_SELECT_COUNT = "SELECT COUNT(";
	String LOCK_FOR_UPDATE = " FOR UPDATE ";
	String LOCK_FOR_UPDATE_LIMIT_ONE = " FOR UPDATE LIMIT 1";
	String LOCK_FOR_UPDATE_NOWAIT = " FOR UPDATE NOWAIT ";
	String LOCK_FOR_UPDATE_NOWAIT_LIMIT_ONE = " FOR UPDATE NOWAIT LIMIT 1";
	String LIMIT_ONE = " LIMIT 1";

	String SQL_UPDATE = "UPDATE ";
	String SQL_DELETE_FROM = "DELETE FROM ";
	String CREATE_TABLE = "CREATE TABLE ";

	String COLUMN_CREATED_DATE = "CREATED_DATE";
	String COLUMN_CREATED_BY = "CREATED_BY";
	String COLUMN_UPDATED_DATE = "UPDATED_DATE";
	String COLUMN_UPDATED_BY = "UPDATED_BY";

	String COLUMN_STATUS = "STATUS";
	String COLUMN_VALUE_STATUS_NEW = "NEW";
	String COLUMN_VALUE_STATUS_HOLD = "HOLD";
	String COLUMN_VALUE_STATUS_ACTIVE = "ACTIVE";
	String COLUMN_VALUE_STATUS_INACTIVE = "INACTIVE";
	
}
