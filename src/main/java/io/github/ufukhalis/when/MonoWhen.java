package io.github.ufukhalis.when;

import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

public class MonoWhen<W> {

    private final Mono<W> object;

    private MonoWhen(Mono<W> object) {
        this.object = object;
    }

    public static <T> MonoWhen<T> of(Mono<T> object) {
        return new MonoWhen<>(object);
    }

    public <R> MonoCase<W, R> condition(Predicate<? super W> predicate, Function<? super W, R> mapper) {
        return new MonoCase<>(predicate, this.object, mapper, null);
    }

    public static final class MonoCase<M, C> {

        private final Predicate<? super M> predicate;
        private final Mono<M> object;
        private final Function<? super M, C> mapper;
        private final MonoCase<M, C> prev;

        private MonoCase(Predicate<? super M> predicate, Mono<M> object, Function<? super M, C> mapper, MonoCase<M, C> prev) {
            this.predicate = predicate;
            this.object = object;
            this.mapper = mapper;
            this.prev = prev;
        }

        public MonoCase<M, C> condition(Predicate<? super M> predicate, Function<? super M, C> mapper) {
            return new MonoCase<>(predicate, this.object, mapper, this);
        }

        public Mono<C> execute() {
            return execute(this, this.predicate, this.mapper).checkpoint();
        }

        private Mono<C> execute(MonoCase<M, C> current, Predicate<? super M> predicate, Function<? super M, C> mapper) {
            return this.object
                    .filter(predicate)
                    .map(mapper).switchIfEmpty(proceed(current));
        }

        private Mono<C> proceed(MonoCase<M, C> current) {
            if (current.prev != null) {
                return execute(current.prev, current.prev.predicate, current.prev.mapper);
            }
            return Mono.empty();
        }
    }
}
