package ru.shipownerproject.exceptions;

public class AlreadyAddedToBaseException extends RuntimeException{

    public AlreadyAddedToBaseException(String msg){
        super(msg + " is already added to base.");
    }
}
