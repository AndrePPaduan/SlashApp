package com.slash.slash.exceptions;

public class UserDoesNotExist extends GenericException {
    public UserDoesNotExist() {
        super("User does not exist");
    }
}
