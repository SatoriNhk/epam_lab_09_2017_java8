package optional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

// https://github.com/java8-course/option
public abstract class Optional<T> {

    private static final None EMPTY = new None();

    public static <T> Optional<T> empty() {
        return EMPTY;
    }

    public static <T> Optional<T> of(T t) {
        return new Some<T>(t);
    }

    public static <T> Optional<T> ofNullable(T t) {
        return null == t ? empty() : of(t);
    }

    private Optional() {}

    private static class None<T> extends Optional<T> {

        @Override
        public Optional<T> filter(Predicate<T> p) {
            return empty();
        }

        @Override
        public <R> Optional<R> map(Function<T, R> f) {
            return empty();
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> f) {
            return empty();
        }

        @Override
        public void forEach(Consumer<T> c) {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T orElse(T t) {
            return t;
        }
    }

    private static class Some<T> extends Optional<T> {

        private final T value;

        public Some(T value) {
            this.value = value;
        }

        @Override
        public Optional<T> filter(Predicate<T> p) {
            return p.test(value) ? this : empty();
        }

        @Override
        public <R> Optional<R> map(Function<T, R> f) {
            return of(f.apply(value));
        }

        @Override
        public <R> Optional<R> flatMap(Function<T, Optional<R>> f) {
            return f.apply(value);
        }

        @Override
        public void forEach(Consumer<T> c) {
            c.accept(value);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public T orElse(T t) {
            return value;
        }
    }


//    filter
    public abstract Optional<T> filter(Predicate<T> p);
//    map
    public abstract <R> Optional<R> map(Function<T, R> f);
//    flatMap
    public abstract <R> Optional<R> flatMap(Function<T, Optional<R>> f);
//    forEach
    public abstract void forEach(Consumer<T> c);
//    isEmpty
    public abstract boolean isEmpty();
//    orElse
    public abstract T orElse(T t);
}









