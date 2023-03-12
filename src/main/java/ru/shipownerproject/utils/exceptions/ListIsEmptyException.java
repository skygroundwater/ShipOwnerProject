package ru.shipownerproject.utils.exceptions;

public class ListIsEmptyException extends RuntimeException{

    public ListIsEmptyException(String msg){
        super("List of " + msg + " is empty");
    }
}
