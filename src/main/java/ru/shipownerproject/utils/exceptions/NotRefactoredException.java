package ru.shipownerproject.utils.exceptions;

public class NotRefactoredException extends RuntimeException{

    public NotRefactoredException(String msg){
        super(msg + " hasn't been refactored.");
    }
}
