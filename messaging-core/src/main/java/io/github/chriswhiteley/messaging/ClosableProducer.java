package io.github.chriswhiteley.messaging;

public interface ClosableProducer<T> extends Producer<T>, AutoCloseable {
    @Override
    void close();
}
