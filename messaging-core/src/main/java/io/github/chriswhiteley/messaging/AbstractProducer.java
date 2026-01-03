package io.github.chriswhiteley.messaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProducer<T> implements Producer<T> {

    /**
     * Determines the destination based on the message.
     */
    protected abstract String getDestination(T message);

    /**
     * Determines a name for the message.
     */
    protected abstract String getMessageName(T message);


    /**
     * Encodes the message into a format suitable for transmission.
     */
    protected abstract String encode(T message);

    /**
     * Sends the message to the resolved destination.
     */
    public final void produce(T message) {
        String messageName = getMessageName(message);
        String destination = getDestination(message);
        String encodedMessage = encode(message);

        try {
            log.debug("Producing message [{}] to destination [{}]", messageName, destination);
            sendToDestination(messageName, destination, encodedMessage);
        } catch (Exception e) {
            log.error("Failed to send message [{}] to destination [{}]", messageName, destination, e);
            throw new MessageProductionException("Message production failed for message: " + messageName, e);
        }
    }

    /**
     * Implementation for sending the encoded message to the destination.
     */
    protected abstract void sendToDestination(String messageName, String destination, String encodedMessage);

}
