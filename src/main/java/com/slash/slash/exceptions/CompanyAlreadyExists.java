package com.slash.slash.exceptions;

public class CompanyAlreadyExists extends GenericException {

    public CompanyAlreadyExists() {
        super("Company Name Already Exists");
    }
}
