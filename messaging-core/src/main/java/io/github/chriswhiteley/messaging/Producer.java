package io.github.chriswhiteley.messaging;

public interface Producer<T> {
    /**
     * Sends data to the intended destination.
     *
     * @param data the data to produce/send
     */
    void produce(T data);
}
