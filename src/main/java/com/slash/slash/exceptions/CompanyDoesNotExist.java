package com.slash.slash.exceptions;

public class CompanyDoesNotExist extends GenericException {
    public CompanyDoesNotExist() {
        super("Company does not exist");
    }
}
