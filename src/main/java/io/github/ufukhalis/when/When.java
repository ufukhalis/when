package io.github.ufukhalis.when;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class When <W> {

    private final W object;

    private When(W object) {
        this.object = object;
    }

    public static <T> When<T> of(T object) {
        return new When<>(object);
    }

    public When.Case<W> condition(Predicate<? super W> predicate) {
        return new Case<>(predicate, this.object, null);
    }

    protected static final class Case<C> {

        private final Predicate<? super C> predicate;
        private final C object;
        private final Return prev;

        private Case(Predicate<? super C> predicate, C object, Return<?, C> prev) {
            this.prev = prev;
            this.predicate = predicate;
            this.object = object;
        }

        public <R> Return<R, C> thenReturn(Function<? super C, ? extends R> mapper) {
            if (object != null && predicate.test(object)) {
                return new Return<R, C>(mapper, this.object, prev);
            }
            return new Return<R, C>(null, this.object, prev);
        }

    }

    protected static final class Return<R, V> {

        private final Supplier<R> supplier;
        private final V object;
        private final Return<R, V> prev;

        private Return(Function<? super V, ? extends R> mapper, V o, Return<R, V> prev) {
            this.prev = prev;
            this.object = o;
            this.supplier = mapper != null ? () ->  mapper.apply(o) : null;
        }

        public When.Case<V> condition(Predicate<? super V> predicate) {
            return new Case<>(predicate, this.object, this);
        }

        public Optional<R> execute() {
            return Optional.ofNullable(execute(this, this.supplier));
        }

        private R execute(Return<R, V> current, Supplier<R> supplier) {

            if (current.supplier != null) {
                supplier = current.supplier;
            }

            if (current.prev != null) {
                return execute(current.prev, supplier);
            }

            if(supplier != null) {
                return supplier.get();
            }

            return null;
        }
    }
}
