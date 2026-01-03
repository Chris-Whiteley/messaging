package io.github.chriswhiteley.messaging.chunk;

import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;

public class Chunks implements Iterable<Chunk>, Iterator<Chunk> {

    @Getter
    private String destination;

    @Getter
    private UUID id;
    private Chunk[] chunks;
    private int index = 0;

    public void add(Chunk chunk) {
        if (chunks == null) {
            this.id = chunk.getId();
            this.destination = chunk.getDestination();
            chunks = new Chunk[chunk.getTotal()];
        }

        try {
            chunks[chunk.getIndex()] = chunk;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ChunkException(String.format("Invalid chunk added to chunks, index out of bounds %s. Chunks size is %d", chunk, chunks.length), ex);
        }

    }

    public int chunkCount() {
        if (chunks == null) return 0;

        int count = 0;

        for (Chunk chunk : chunks) {
            if (chunk != null) count++;
        }

        return count;

    }

    public int size() {
        if (chunks == null) return 0;
        return chunks.length;
    }


    public boolean isComplete() {
        if (chunks == null) return false;

        for (Chunk chunk : chunks) {
            if (chunk == null) return false;
        }

        return true;
    }

    public String getMessage() {
        if (!isComplete())
            throw new ChunkException("can't obtain message all chunks have not been added using the add() method");

        StringBuilder message = new StringBuilder();

        for (Chunk chunk : chunks) {
            message.append(chunk.getMessageChunk());
        }

        return message.toString();
    }

    @Override
    public Iterator<Chunk> iterator() {
        index = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < chunks.length;
    }

    @Override
    public Chunk next() {
        if (index >= chunks.length) throw new NoSuchElementException();
        return chunks[index++];
    }

    public boolean isEmpty() {
        return chunkCount() == 0;
    }
}
