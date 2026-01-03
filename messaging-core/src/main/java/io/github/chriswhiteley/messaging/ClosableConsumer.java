package io.github.chriswhiteley.messaging;

public interface ClosableConsumer<T> extends Consumer<T>, AutoCloseable {
    @Override
    void close();
}
