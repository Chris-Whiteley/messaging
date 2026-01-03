package io.github.chriswhiteley.messaging;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;

@Slf4j
public abstract class AbstractConsumer<T> implements Consumer<T> {

    /**
     * Retrieves the source from which the message will be consumed.
     */
    protected abstract String getSource();

    /**
     * Decodes the message into a suitable format for processing.
     */
    protected abstract T decode(String encodedMessage);

    /**
     * Retrieves and processes data when available, blocks until data is received or the timeout occurs.
     *
     * @param timeout the maximum time to wait as a Duration
     * @return an Optional containing the data if received, or empty if timed out
     */
    @Override
    public final Optional<T> consume(Duration timeout) {
        String source = getSource();

        try {
            log.debug("Attempting to consume message from source [{}] with timeout [{}]", source, timeout);

            // Simulating message retrieval process (could be from a queue, topic, etc.)
            String encodedMessage = retrieveMessage(timeout); // This method needs to be implemented
            if (encodedMessage != null) {
                T message = decode(encodedMessage);
                log.debug("Successfully consumed message from source [{}]", source);
                return Optional.of(message);
            } else {
                log.debug("No message received within the timeout period from source [{}]", source);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Failed to consume message from source [{}] with timeout [{}]", source, timeout, e);
            throw new MessageConsumptionException("Message consumption failed for source: " + source, e);
        }
    }

    /**
     * Simulates the process of retrieving the encoded message (this should be implemented by subclasses).
     * It could represent the logic to pull messages from a queue, topic, or other source.
     *
     * @param timeout the maximum duration to wait for the message
     * @return the encoded message, or null if no message was available within the timeout period
     */
    protected abstract String retrieveMessage(Duration timeout);

}
