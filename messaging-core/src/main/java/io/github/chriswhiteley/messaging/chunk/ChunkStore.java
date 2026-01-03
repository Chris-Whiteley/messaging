package io.github.chriswhiteley.messaging.chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ChunkStore {

    private Map<UUID, Chunks> chunksMap = new HashMap<>();

    public Chunks store(Chunk chunk) {
        Chunks chunks = chunksMap.computeIfAbsent(chunk.getId(), id -> new Chunks());
        chunks.add(chunk);
        return chunks;
    }

    public void remove(UUID chunkId) {
        chunksMap.remove(chunkId);
    }

    public void clear() {
        chunksMap.clear();
    }
}
