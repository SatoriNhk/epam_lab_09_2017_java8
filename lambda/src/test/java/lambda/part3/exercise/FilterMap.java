package lambda.part3.exercise;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class FilterMap {

    public static class Container<T, R> {

        private final Predicate<T> predicate;
        private final Function<T, R> function;

        public Container(Predicate<T> predicate) {
            this.predicate = predicate;
            this.function = null;
        }

        public Container(Function<T, R> function) {
            this.function = function;
            this.predicate = null;
        }

        public Predicate<T> getPredicate() {
            return predicate;
        }

        public Function<T, R> getFunction() {
            return function;
        }
    }

    public static class LazyCollectionHelper<T> {

        private final List<Container<Object, Object>> actions;

        private final List<T> list;

        public LazyCollectionHelper(List<T> list, List<Container<Object, Object>> actions) {
            this.actions = actions;
            this.list = list;
        }

        public LazyCollectionHelper(List<T> list) {
            this(list, new ArrayList<>());
        }

        public LazyCollectionHelper<T> filter(Predicate<? super T> condition) {
            List<Container<Object, Object>> actions = new ArrayList<>(this.actions);
            actions.add(new Container<>((Predicate<Object>) condition));
            return new LazyCollectionHelper<T>(list, actions);
        }

        public <R> LazyCollectionHelper<R> map(Function<? super T, ? extends R> function) {
            List<Container<Object, Object>> actions = new ArrayList<>(this.actions);
            actions.add(new Container<>((Function<Object, Object>) function));
            return new LazyCollectionHelper<>((List<R>)list, actions);
        }

        public List<T> force() {

            if (!actions.isEmpty()) {

                List<T> result = new ArrayList<>();
                nextValue: for (T value : list) {
                    for (Container action : actions) {
                        Predicate<Object> predicate = action.getPredicate();
                        if (predicate != null) {
                            if(!predicate.test(value)) {
                                continue nextValue;
                            }
                        } else {
                            Function<Object, Object> function = action.getFunction();
                            value = (T) function.apply(value);
                        }
                    }
                    result.add(value);
                }
                return result;
            }
            else return new ArrayList<>(list);
        }
    }



    @Test
    public void test() {
        List<Integer> integers = Arrays.asList(1, 2, 100, 110, 200, 300, 500);


//        LazyCollectionHelper<Integer> lazy = new LazyCollectionHelper<>(integers);
//        LazyCollectionHelper<Integer> lazy2 = lazy.filter(val -> val != 0);
//        LazyCollectionHelper<Integer> lazy3 = lazy2.filter(val -> val < 0);
//        LazyCollectionHelper<Double> lazy4 = lazy3.map(Double::valueOf);
//
//        List<Double> lazyResult = lazy4.force();


        List<String> result = new LazyCollectionHelper<>(integers).filter(val -> val > 10)
                                                                  .filter(val -> val < 400)
                                                                  .map(Object::toString)
                                                                  .filter(str -> str.startsWith("1"))
                                                                  .force();

        assertEquals(Arrays.asList("100", "110"), result);
    }
}
