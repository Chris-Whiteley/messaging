# Messaging

A Java messaging abstraction that separates message production and consumption
from transport-specific implementations such as Kafka.

## Modules

- **messaging-core**  
  Core messaging interfaces and contracts.

- **kafka-messaging**  
  Kafka-based implementation of the messaging abstractions.

## Design Goals

- Transport-agnostic core
- Clean separation of concerns
- Designed for event-driven and message-based systems
