package com.iu.acadia.microservicio.exceptions;

public class DocumentNotFound extends Throwable {
    public DocumentNotFound(String message) {
        super(message);
    }
}
