package io.github.ufukhalis.when;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class NormalWhen<W> {

    private final W object;

    private NormalWhen(W object) {
        this.object = object;
    }

    static <T> NormalWhen<T> of(T object) {
        return new NormalWhen<>(object);
    }

    static <T> NormalWhen<T> of(Optional<T> object) {
        return new NormalWhen<>(object.orElse(null));
    }

    public <R> NormalWhen.Case<W, R> condition(Predicate<? super W> predicate, Function<? super W, R> mapper) {
        return new Case<>(predicate, this.object, null, mapper);
    }

    public static final class Case<M, C> {

        private final Predicate<? super M> predicate;
        private final Function<? super M, C> mapper;
        private final M object;
        private final Case<M, C> prev;

        private Case(Predicate<? super M> predicate, M object, Case<M, C> prev, Function<? super M, C> mapper) {
            this.prev = prev;
            this.predicate = predicate;
            this.object = object;
            this.mapper = mapper;
        }

        public Case<M, C> condition(Predicate<? super M> predicate, Function<? super M, C> mapper) {
            return new Case<>(predicate, this.object, this, mapper);
        }

        public Optional<C> toOptional() {
            return Optional.ofNullable(execute(this));
        }

        private C execute(Case<M, C> current) {

            if (this.object != null && current.predicate.test(this.object)) {
                return current.mapper.apply(this.object);
            }

            if (current.prev != null) {
                return execute(current.prev);
            }

            return null;
        }

        public C getOrElseGet(Supplier<? extends C> supplier) {
            return toOptional().orElseGet(supplier);
        }

        public C getOrElse(C other) {
            return toOptional().orElse(other);
        }

    }


}
