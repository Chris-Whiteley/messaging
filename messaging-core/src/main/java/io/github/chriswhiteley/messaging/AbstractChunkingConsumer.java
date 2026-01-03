package io.github.chriswhiteley.messaging;

import io.github.chriswhiteley.messaging.chunk.Chunk;
import io.github.chriswhiteley.messaging.chunk.ChunkStore;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;

@Slf4j
public abstract class AbstractChunkingConsumer<T> implements Consumer<T> {

    private final ChunkStore chunkStore;

    // Constructor for dependency injection of ChunkStore
    protected AbstractChunkingConsumer() {
        this.chunkStore = new ChunkStore(); // You may inject a ChunkStore if required
    }

    /**
     * Retrieves the source from which the message will be consumed.
     */
    protected abstract String getSource();


    /**
     * Decodes the message into a suitable format for processing.
     */
    protected abstract T decode(String encodedMessage);

    /**
     * Implementation for retrieving and processing chunks from a source.
     */
    @Override
    public final Optional<T> consume(Duration timeout) {
        String source = getSource();
        long startTime = System.currentTimeMillis();
        long timeoutMillis = timeout.toMillis();

        try {
            log.debug("Attempting to consume chunked message from source [{}] with timeout [{}]", source, timeout);

            while (true) {
                // Calculate how much time is left to retrieve the chunk
                long remainingTime = timeoutMillis - (System.currentTimeMillis() - startTime);

                // If timeout has passed, stop consuming
                if (remainingTime <= 0) {
                    log.warn("Timed out waiting for all chunks from source [{}]", source);
                    return Optional.empty();
                }

                // Simulate chunk retrieval process with remaining time
                Chunk chunk = retrieveChunk(Duration.ofMillis(remainingTime));

                if (chunk != null) {
                    // Store the chunk in ChunkStore and check if message is complete
                    var chunks = chunkStore.store(chunk);

                    if (chunks.isComplete()) {
                        String encodedMessage = chunks.getMessage();
                        T fullMessage = decode(encodedMessage); // Decode the message
                        chunkStore.remove(chunks.getId()); // Remove the chunks once they are processed
                        log.debug("Successfully reassembled full message [{}] from source [{}]", chunk.getName(), source);
                        return Optional.of(fullMessage);
                    }
                } else {
                    log.debug("No chunk received within the timeout period from source [{}]", source);
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            log.error("Failed to consume chunked message from source [{}] with timeout [{}]", source, timeout, e);
            throw new MessageConsumptionException("Message consumption failed for source: " + source, e);
        }
    }

    /**
     * Simulates the process of retrieving the next chunk.
     * This should be implemented by subclasses to fetch the actual chunk from a source.
     *
     * @param timeout the maximum duration to wait for the chunk
     * @return the next chunk, or null if no chunk is available within the timeout
     */
    protected abstract Chunk retrieveChunk(Duration timeout);

}

