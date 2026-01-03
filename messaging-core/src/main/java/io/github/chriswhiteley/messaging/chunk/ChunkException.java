package io.github.chriswhiteley.messaging.chunk;

public class ChunkException extends RuntimeException {
    public ChunkException(String msg) {
        super(msg);
    }

    public ChunkException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
