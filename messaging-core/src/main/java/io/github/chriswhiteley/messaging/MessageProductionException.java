package io.github.chriswhiteley.messaging;

public class MessageProductionException extends RuntimeException {

    // Constructor that accepts a message and the cause of the exception
    public MessageProductionException(String message) {
        super(message);
    }

    // Constructor that accepts a message, cause, and custom data (e.g., message name)
    public MessageProductionException(String message, Throwable cause) {
        super(message, cause);
    }

    // Optionally add more fields, like error codes or timestamps, if needed
}

