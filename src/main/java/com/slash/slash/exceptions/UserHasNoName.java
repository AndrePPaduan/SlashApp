package com.slash.slash.exceptions;

public class UserHasNoName extends GenericException {

    public UserHasNoName() {
        super("User has no name or surname");
    }
}

