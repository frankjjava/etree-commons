package com.etree.commons.core.dto;

import lombok.Data;

@Data
public class MessageDto {
	
	private Boolean success;
	private Object response;
	private String message;
	private Errors errors;

}
