package io.github.chriswhiteley.messaging;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface Stream<K, V> {
    Stream<K, V> filter(BiPredicate<K, V> var1);

    void forEach(BiConsumer<K, V> var1);

    void start();

    void stop();

    void close();
}
