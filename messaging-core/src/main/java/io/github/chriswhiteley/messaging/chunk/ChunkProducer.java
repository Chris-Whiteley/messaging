package io.github.chriswhiteley.messaging.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkProducer {
    private final int maxChunkMsgSize;

    public ChunkProducer(int chunkSize) {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("chunkSize must be greater than 0");
        }
        this.maxChunkMsgSize = chunkSize;
    }


    public Chunks toChunks(MessageToChunk messageToChunk) {
        String message = messageToChunk.getMessage();
        int messageLength = message.length();
        List<String> messageChunks = new ArrayList<>();

        for (int start = 0; start < messageLength; start += maxChunkMsgSize) {
            int end = Math.min(start + maxChunkMsgSize, messageLength);
            messageChunks.add(message.substring(start, end));
        }

        Chunks chunks = new Chunks();
        UUID chunksId = UUID.randomUUID();

        for (int i = 0; i < messageChunks.size(); i++) {
            chunks.add(
                    Chunk.builder()
                            .id(chunksId)
                            .name(messageToChunk.getName())
                            .destination(messageToChunk.getDestination())
                            .index(i)
                            .total(messageChunks.size())
                            .messageChunk(messageChunks.get(i))
                            .build()
            );
        }
        return chunks;
    }

}
