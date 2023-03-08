package ru.shipownerproject.utils.exceptions;

public class NotFoundInBaseException extends RuntimeException {

    public NotFoundInBaseException(String msg) {
        super(msg + " Please at first it should be in the data base.");
    }
}