package com.etree.commons.core.dto;

import lombok.Data;

@Data
public class MessageDto {
	
	private String message;
	private Boolean success;
	private Object response;
	private Errors errors;

}
