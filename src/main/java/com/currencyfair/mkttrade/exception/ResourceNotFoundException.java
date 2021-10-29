package com.currencyfair.mkttrade.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String table, String column, String value) {
        super("Table: "+table+", Column: "+column+", value: "+value+"; Record not found");
    }
}
