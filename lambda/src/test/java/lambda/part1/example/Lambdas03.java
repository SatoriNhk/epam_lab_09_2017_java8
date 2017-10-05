package lambda.part1.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Convert2Lambda", "FieldCanBeLocal"})
public class Lambdas03 {

    // SAM interface
    //

    @FunctionalInterface
    private interface GenericSum<T> {

        T sum(T a, T b);

        default T twice(T t) {
            return sum(t, t);
        }
    }

    @Test
    public void generic0() {
        GenericSum<Integer> sum =
                new GenericSum<Integer>() {
                    @Override
                    public Integer sum(Integer i1, Integer i2) {
                        System.out.print("before sum");
                        return i1 + i2;
                    }
                };

        assertEquals(Integer.valueOf(3), sum.sum(1, 2));
    }

    @Test
    public void generic1() {
        // Statement lambda
        GenericSum<Integer> sum =
                (i1, i2) -> {
                    System.out.print("before sum");
                    return i1 + i2;
                };

        assertEquals(sum.sum(1, 2), Integer.valueOf(3));
    }

    @Test
    public void generic2() {
        GenericSum<Integer> sum = (i1, i2) -> i1 + i2;

        assertEquals(sum.twice(1), Integer.valueOf(2));
        assertEquals(sum.sum(1, 2), Integer.valueOf(3));
    }

    private static String stringSum(String s1, String s2) {
        return s1 + s2;
    }

    @Test
    public void strSum() {
        // Class method-reference lambda
        GenericSum<String> methodReference = Lambdas03::stringSum;

        GenericSum<String> anonymousClazz = new GenericSum<String>() {
            @Override
            public String sum(String a, String b) {
                return stringSum(a, b);
            }
        };


        assertEquals("12", anonymousClazz.sum("1", "2"));

        assertEquals(methodReference.sum("a", "b"), "ab");
    }

    private final String delimiter = "-";

    private String stringSumWithDelimiter(String s1, String s2) {
        return s1 + delimiter + s2;
    }

    @Test
    public void strSum2() {
        // Object method-reference lambda
        GenericSum<String> sum = this::stringSumWithDelimiter;

        assertEquals(sum.sum("a", "b"), "a-b");
    }

}
