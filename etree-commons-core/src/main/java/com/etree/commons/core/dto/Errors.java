package com.etree.commons.core.dto;

import java.util.ArrayList;
import java.util.List;


public class Errors {

    protected List<Error> error;

    public List<Error> getError() {
        if (error == null) {
            error = new ArrayList<Error>();
        }
        return this.error;
    }

}
