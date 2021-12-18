package com.uol.compasso.productms.exception;

public class ParamsInvalidException extends Exception {

    public ParamsInvalidException(String fields) {
        super(String.format("The request has invalid field values: %s", fields));
    }
}
