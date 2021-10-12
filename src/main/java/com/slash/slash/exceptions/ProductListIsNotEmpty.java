package com.slash.slash.exceptions;

public class ProductListIsNotEmpty extends GenericException {
    public ProductListIsNotEmpty() {
        super("This company has products. Delete the product before deleting the company");
    }
}
