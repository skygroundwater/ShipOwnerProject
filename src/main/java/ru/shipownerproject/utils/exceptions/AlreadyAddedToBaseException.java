package ru.shipownerproject.utils.exceptions;

public class AlreadyAddedToBaseException extends RuntimeException {

    public AlreadyAddedToBaseException(String msg) {
        super(msg + " is already added to base.");
    }
}