package com.slash.slash.exceptions;

public class ProductAlreadyExists extends GenericException {

    public ProductAlreadyExists() {
        super("Product Name Already Exists");
    }
}