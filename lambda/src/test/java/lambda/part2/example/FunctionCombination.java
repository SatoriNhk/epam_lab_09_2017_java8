package lambda.part2.example;

import data.Person;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("UnnecessaryLocalVariable")
public class FunctionCombination {

    @Test
    public void personToInt0() {
        // Person -> Integer
        Function<Person, Integer> lastNameLength = p -> p.getLastName().length();

        assertEquals(5, lastNameLength.apply(new Person("a", "abcde", 0)).intValue());
    }

    // A -> B
    // B -> C
    // A -> C

    // (Person -> String, String -> Integer) -> (Person -> Integer)
    private Function<Person, Integer> personStringPropertyToInt(
            Function<Person, String> personToString,
            Function<String, Integer> stringToInteger) {
        return person -> stringToInteger.apply(personToString.apply(person));
    }

    @Test
    public void personToInt1() {
        Function<Person, String> getLastName = Person::getLastName;
        Function<String, Integer> getLength = String::length;
        Function<Person, Integer> lastNameLength = personStringPropertyToInt(getLastName, getLength);

        assertEquals(5, lastNameLength.apply(new Person("a", "abcde", 0)).intValue());
    }

    // (A -> B, B -> C) -> (A -> C)
    private <A, B, C> Function<A, C> andThen(Function<A, B> f1, Function<B, C> f2) {
        return a -> f2.apply(f1.apply(a));
    }

    @Test
    public void personToInt2() {
        Function<Person, String> getLastName = Person::getLastName;
        Function<String, Integer> getLength = String::length;
        Function<Person, Integer> lastNameLength = andThen(getLastName, getLength);

        assertEquals(5, lastNameLength.apply(new Person("a", "abcde", 0)).intValue());
    }


    static class MyClass {

    }

    static class InheritedMyClass extends MyClass {

    }

    @Test
    public void personToInt3() {
        Function<Person, String> getLastName = Person::getLastName;
        Function<String, Integer> getLength = String::length;
        Function<Person, Integer> lastNameLength = getLastName.andThen(getLength);

        MyClass ref = new InheritedMyClass();
        Class<? extends MyClass> myClassClazz = ref.getClass();


        Class<? extends Integer> integerClazz = Integer.valueOf(10).getClass();

        Class<MyClass> reallyIntegerClazz = MyClass.class;


        List<String> list = new ArrayList<>();
        List<Integer> listIntegers = new ArrayList<>();
        list.add("1");

        String val = (String)list.get(0);


        List rawReference = list;
        rawReference.add(new MyClass());

        System.out.println(list.size());

        List<String> hardCheckedList = Collections.checkedList(list, String.class);

        List rawReference2 = hardCheckedList;
//        rawReference2.add(new MyClass());  <- Упадем с ClassCastException

        System.out.println(list.size());

//        hardCheckedList

        for (Method method : Person.class.getDeclaredMethods()) {
            System.out.println(method);
        }

        try {
            Method compareToPerson = Person.class.getMethod("compareTo", Person.class);
            Method compareToObject = Person.class.getMethod("compareTo", Object.class);

            System.out.println(compareToPerson.isSynthetic());
            System.out.println(compareToObject.isSynthetic());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // Person.compareTo both methods has same erasure

        // GenericException

//        List<String>[] arrayOfLists = new List<String>[10];
        Integer[] integers = new Integer[10];
        integers[0] = 100;



        Number[] numbers = integers;
//        numbers[1] = 200d; <- ArrayStoreException


        Number numVal = 10;

//        List<Object> objectList = new ArrayList<String>();
        List<? extends Number> listNumbers = new ArrayList<Double>();
//        Number numberVal = listNumbers.get(0);

        listNumbers.add(null);



        List<? super Number> listSuperNumbers = new ArrayList<Object>();
        listSuperNumbers.add(1);
        listSuperNumbers.add(1L);
        listSuperNumbers.add(1d);
        listSuperNumbers.add(1f);
        listSuperNumbers.add((short)1);

        Object obj = listSuperNumbers.get(0);

        // Producer
        // Extends
        // Consumer
        // Super


        // Array generics
        // Raw types list (Int -> Double)

        assertEquals(5, lastNameLength.apply(new Person("a", "abcde", 0)).intValue());

        List<Number> intList = Arrays.asList(1, 23, 4);
        Comparator<Integer> intComparator = Integer::compareTo;
        Comparator<Object> objectComparator = (o1, o2) -> 0;

        Number[] numberArray = new Integer[10];

        List<String> first = new ArrayList<>(Arrays.asList("1", "2"));
        List<String> second = new ArrayList<>(Arrays.asList("a", "b"));
        varArgsMethod(first, second);
    }


    public static void varArgsMethod(List<String>...lists) {
        Object[] objects = lists;
        objects[0] = new ArrayList<>(Arrays.asList(100, 200, 300));

        Object str = (Object)lists[0].get(0);
        System.out.println(str);
    }

    public static Integer findMaxInt(List<Integer> list, Comparator<Integer> comparator) {
        return null;
    }

    public static String findMaxString(List<String> list, Comparator<String> comparator) {
        return null;
    }

    public static <T> T findMax(List<? extends T> list, Comparator<? super T> comparator) {
        return null;
    }

    class MyException extends RuntimeException {

    }

    // (int, int, int) -> int
    public static int sum(int x, int y, int z) {
        return x + y + z;
    }

    // int -> int -> int -> int
    public static Function<Integer, Function<Integer, Function<Integer, Integer>>> curry() {
        return x -> {
          return y -> {
            return z -> {
                return sum(x, y, z);
            };
          };
        };
    }

    public void method() {
        Object a = null;
        Object b = null;

        boolean eq = (a != null) ? a.equals(b) : b == null;
        boolean eq7Java = Objects.equals(a, b);
    }

    @Test
    public void testCurry() {
        Supplier<Function<Integer, Function<Integer, Function<Integer, Integer>>>> func = FunctionCombination::curry;
        assertEquals(6, func.get().apply(1).apply(2).apply(3).intValue());
    }
}
