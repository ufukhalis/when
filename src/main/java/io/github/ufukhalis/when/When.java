package io.github.ufukhalis.when;

import reactor.core.publisher.Mono;

import java.util.Optional;

public final class When {

    private When() {
    }

    public static <T> NormalWhen<T> of(T object) {
        return NormalWhen.of(object);
    }

    public static <T> NormalWhen<T> of(Optional<T> object) {
        return NormalWhen.of(object);
    }

    public static <T> MonoWhen<T> of(Mono<T> object) {
        return MonoWhen.of(object);
    }

}
