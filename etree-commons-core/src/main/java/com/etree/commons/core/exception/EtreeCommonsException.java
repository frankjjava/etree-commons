/**
* Copyright © 2020 eTree Technologies Pvt. Ltd.
*
* @author  Franklin Joshua
* @version 1.0
* @since   2020-11-04 
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
