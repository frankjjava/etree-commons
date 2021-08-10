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
package com.etree.commons.core.exception;

public class EtreeCommonsException extends RuntimeException {
	private static final long serialVersionUID = -1871120411165458375L;
	protected String errorCode;

	public EtreeCommonsException(String errorCode) {
		this.errorCode = errorCode;
	}

	public EtreeCommonsException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public EtreeCommonsException(Throwable cause) {
		super(cause);
	}

	public EtreeCommonsException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public EtreeCommonsException(String errorCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
