package io.github.chriswhiteley.messaging.chunk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class ChunkCodec {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Chunk decode(String jsonChunk) {
        try {
            ObjectNode objectNode = (ObjectNode) OBJECT_MAPPER.readTree(jsonChunk);
            UUID id = UUID.fromString(objectNode.get("i").asText());
            int index = objectNode.get("x").asInt();
            int total = objectNode.get("t").asInt();
            String name = objectNode.get("n").asText();
            String messageChunk = objectNode.get("m").asText();
            return Chunk.builder()
                    .id(id)
                    .name(name)
                    .index(index)
                    .total(total)
                    .messageChunk(messageChunk)
                    .build();
        } catch (IOException ex) {
            log.error("Error decoding Event from JSON string {}", jsonChunk, ex);
            return null;
        }
    }

    public String encode(Chunk chunk) {
        ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        objectNode.put("i", chunk.getId().toString());
        objectNode.put("x", chunk.getIndex());
        objectNode.put("t", chunk.getTotal());
        objectNode.put("n", chunk.getName());
        objectNode.put("m", chunk.getMessageChunk());
        return objectNode.toString();
    }
}
