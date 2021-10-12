package com.slash.slash.exceptions;

public class UserRoleNotFoundException extends GenericException {
    public UserRoleNotFoundException() {
        super("User role does not exist");
    }
}
