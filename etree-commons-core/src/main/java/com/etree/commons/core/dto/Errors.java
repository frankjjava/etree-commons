package com.etree.commons.core.dto;

import java.util.List;

import lombok.Data;

@Data
public class Errors {

    protected List<Error> error;

    @Data
    public static class Error {

    	private String errCode;
    	private String errMessage;

    }
}
