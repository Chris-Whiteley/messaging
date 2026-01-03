package io.github.chriswhiteley.messaging;

import java.time.Duration;
import java.util.Optional;

public interface Consumer<T> {
    /**
     * Retrieves and processes data when available, blocks until data is received or the timeout occurs.
     *
     * @param timeout the maximum time to wait as a Duration
     * @return an Optional containing the data if received, or empty if timed out
     */
    Optional<T> consume(Duration timeout);
}
