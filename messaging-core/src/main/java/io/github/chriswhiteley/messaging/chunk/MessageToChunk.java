package io.github.chriswhiteley.messaging.chunk;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageToChunk {
    private final String name;
    private final String destination;
    private final String message;
}
