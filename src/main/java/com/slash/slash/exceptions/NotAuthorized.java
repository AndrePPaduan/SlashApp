package com.slash.slash.exceptions;

public class NotAuthorized  extends GenericException {
    public NotAuthorized() {
        super("No authorization for such action");
    }
}
