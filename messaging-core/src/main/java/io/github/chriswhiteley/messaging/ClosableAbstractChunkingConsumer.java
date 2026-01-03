package io.github.chriswhiteley.messaging;

public abstract class ClosableAbstractChunkingConsumer<T> extends AbstractChunkingConsumer<T> implements ClosableConsumer<T> {
    @Override
    public abstract void close();
}