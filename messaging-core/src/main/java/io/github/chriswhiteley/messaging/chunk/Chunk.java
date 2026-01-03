package io.github.chriswhiteley.messaging.chunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
public class Chunk {
    private static final ChunkCodec CODEC = new ChunkCodec();
    private final UUID id;
    private final String destination;
    private final String name;
    private int index;
    private int total;
    private final String messageChunk;

    public String encodeToJson() {
        return CODEC.encode(this);
    }

    public static Chunk decodeFromJson (String json) {
        return CODEC.decode(json);
    }

}
