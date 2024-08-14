package com.bookstore.Book_Service.exception;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(String message) {
        super(message);
    }
}
