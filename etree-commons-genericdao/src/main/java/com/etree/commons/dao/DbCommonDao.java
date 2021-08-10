/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-commons.
* 
*  The etree-commons is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The etree-commons is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with etree-commons project.  If not, see <https://www.gnu.org/licenses/>.
*
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
