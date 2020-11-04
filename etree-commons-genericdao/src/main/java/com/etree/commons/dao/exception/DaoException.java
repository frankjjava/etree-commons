/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2016-01-15 
*/
package com.etree.commons.dao.exception;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -1871120411165458375L;

	protected String errorCode; 

	public DaoException(String errorCode) {
		this.errorCode = errorCode;
	}

	public DaoException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public DaoException(String errorCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
